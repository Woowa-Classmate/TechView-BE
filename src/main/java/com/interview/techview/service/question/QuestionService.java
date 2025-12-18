package com.interview.techview.service.question;

import com.interview.techview.domain.category.Category;
import com.interview.techview.domain.question.Difficulty;
import com.interview.techview.domain.question.Question;
import com.interview.techview.domain.skill.Skill;
import com.interview.techview.dto.question.QuestionCreateRequest;
import com.interview.techview.dto.question.QuestionResponse;
import com.interview.techview.dto.question.QuestionUpdateRequest;
import com.interview.techview.exception.CustomException;
import com.interview.techview.exception.ErrorCode;
import com.interview.techview.repository.category.CategoryRepository;
import com.interview.techview.repository.question.QuestionCategoryRepository;
import com.interview.techview.repository.question.QuestionRepository;
import com.interview.techview.repository.question.QuestionSkillRepository;
import com.interview.techview.repository.skill.SkillRepository;
import com.interview.techview.dto.common.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final CategoryRepository categoryRepository;
    private final SkillRepository skillRepository;

    private final QuestionCategoryRepository questionCategoryRepository;
    private final QuestionSkillRepository questionSkillRepository;

    // 질문 생성
    @Transactional
    public QuestionResponse create(QuestionCreateRequest request) {

        // 질문 저장
        Question question = Question.builder()
                .question(request.getQuestion())
                .answer(request.getAnswer())
                .difficulty(request.getDifficulty())
                .build();

        questionRepository.save(question);

        // 카테고리/스킬 조회 및 매핑
        List<Category> categories = categoryRepository.findAllById(request.getCategories());
        if (categories.size() != request.getCategories().size()) {
            throw new CustomException(ErrorCode.CATEGORY_NOT_FOUND);
        }

        List<Skill> skills = skillRepository.findAllById(request.getSkills());
        if (skills.size() != request.getSkills().size()) {
            throw new CustomException(ErrorCode.SKILL_NOT_FOUND);
        }

        // 엔티티 메서드로 연관관계 설정
        question.setCategories(categories);
        question.setSkills(skills);

        return QuestionResponse.from(question);
    }


    // 모든 질문 조회 (관리자용)
    @Transactional(readOnly = true)
    public List<QuestionResponse> getAll() {
        return questionRepository.findAll()
                .stream()
                .map(QuestionResponse::from)
                .toList();
    }

    // 질문 조회 (검색)
    @Transactional(readOnly = true)
    public List<QuestionResponse> search(Long categoryId, Long skillId, Difficulty difficulty, String keyword) {
        return questionRepository.searchQuestions(categoryId, skillId, difficulty, keyword)
                .stream()
                .map(QuestionResponse::from)
                .toList();
    }


    // 질문 상세 조회
    @Transactional(readOnly = true)
    public QuestionResponse getDetail(Long id) {
        Question q = questionRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.QUESTION_NOT_FOUND));

        return QuestionResponse.from(q);
    }


    // 질문 수정
    @Transactional
    public void update(Long id, QuestionUpdateRequest request) {

        Question q = questionRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.QUESTION_NOT_FOUND));

        String question = q.getQuestion();
        String answer = q.getAnswer();
        Difficulty difficulty = q.getDifficulty();

        if (request.getQuestion() != null) { question = request.getQuestion(); }
        if (request.getAnswer() != null) { answer = request.getAnswer(); }
        if (request.getDifficulty() != null) { difficulty = request.getDifficulty();}

        // 질문 내용 수정
        q.update(question, answer, difficulty);

        // 분야 수정
        if (request.getCategories() != null) {
            // 기존 매핑 제거
            questionCategoryRepository.deleteByQuestionId(q.getId());
            questionCategoryRepository.flush();

            List<Category> categories = categoryRepository.findAllById(request.getCategories());
            if (categories.size() != request.getCategories().size()) {
                throw new CustomException(ErrorCode.CATEGORY_NOT_FOUND);
            }

            q.setCategories(categories);  // orphanRemoval 로 기존 것은 자동 삭제
        }

        // 기술스택 수정
        if (request.getSkills() != null) {
            // 기존 매핑 제거
            questionSkillRepository.deleteByQuestionId(q.getId());
            questionSkillRepository.flush();

            List<Skill> skills = skillRepository.findAllById(request.getSkills());
            if (skills.size() != request.getSkills().size()) {
                throw new CustomException(ErrorCode.SKILL_NOT_FOUND);
            }

            q.setSkills(skills);
        }
    }


    // 질문 삭제
    @Transactional
    public void delete(Long id) {
        questionRepository.deleteById(id);
    }


    // 추천
    @Transactional(readOnly = true)
    public List<QuestionResponse> recommend(Difficulty difficulty, Long categoryId, Long skillId) {
        return questionRepository.recommend(difficulty, categoryId, skillId)
                .stream()
                .map(QuestionResponse::from)
                .toList();
    }

    // 포지션별 질문 조회
    @Transactional(readOnly = true)
    public List<QuestionResponse> getQuestionsByPosition(String positionName) {
        // 카테고리 이름으로 조회 (대소문자 무시)
        Category category = categoryRepository.findByNameIgnoreCase(positionName)
                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));

        // 해당 카테고리의 모든 질문 조회
        return questionRepository.searchQuestions(category.getId(), null, null, null)
                .stream()
                .map(QuestionResponse::from)
                .toList();
    }

    // 포지션별 질문 조회 (페이징)
    @Transactional(readOnly = true)
    public PageResponse<QuestionResponse> getQuestionsByPosition(String positionName, String difficultyOrder, Pageable pageable) {
        // 카테고리 이름으로 조회 (대소문자 무시)
        Category category = categoryRepository.findByNameIgnoreCase(positionName)
                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));

        // 해당 카테고리의 모든 질문 조회
        List<Question> allQuestions = questionRepository.searchQuestions(category.getId(), null, null, null);
        
        // 난이도 정렬 (EASY -> MEDIUM -> HARD 순서)
        Comparator<Question> difficultyComparator = Comparator.comparing(q -> {
            Difficulty d = q.getDifficulty();
            return switch (d) {
                case EASY -> 1;
                case MEDIUM -> 2;
                case HARD -> 3;
            };
        });
        
        // 정렬 방향에 따라 정렬
        if ("desc".equalsIgnoreCase(difficultyOrder)) {
            allQuestions = allQuestions.stream()
                    .sorted(difficultyComparator.reversed())
                    .collect(Collectors.toList());
        } else {
            allQuestions = allQuestions.stream()
                    .sorted(difficultyComparator)
                    .collect(Collectors.toList());
        }
        
        // 페이징 처리
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), allQuestions.size());
        List<Question> pagedQuestions = allQuestions.subList(start, end);
        
        List<QuestionResponse> content = pagedQuestions.stream()
                .map(QuestionResponse::from)
                .toList();
        
        return PageResponse.of(
                content,
                allQuestions.size(),
                pageable.getPageNumber(),
                pageable.getPageSize()
        );
    }
}

