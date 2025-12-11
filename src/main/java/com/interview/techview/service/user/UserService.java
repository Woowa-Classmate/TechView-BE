package com.interview.techview.service.user;

import com.interview.techview.domain.category.Category;
import com.interview.techview.domain.skill.Skill;
import com.interview.techview.domain.user.User;
import com.interview.techview.dto.user.UpdateUserProfileRequest;
import com.interview.techview.dto.user.UserProfileResponse;
import com.interview.techview.exception.CustomException;
import com.interview.techview.exception.ErrorCode;
import com.interview.techview.repository.category.CategoryRepository;
import com.interview.techview.repository.skill.SkillRepository;
import com.interview.techview.repository.user.UserCategoryRepository;
import com.interview.techview.repository.user.UserRepository;
import com.interview.techview.repository.user.UserSkillRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final SkillRepository skillRepository;
    private final UserCategoryRepository userCategoryRepository;
    private final UserSkillRepository userSkillRepository;

    // 내 정보 조회
    @Transactional(readOnly = true)
    public UserProfileResponse getMyInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        return UserProfileResponse.from(user);
    }

    // 내 정보 변경
    @Transactional
    public void updateMyInfo(Long userId, UpdateUserProfileRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        user.updateName(request.getName());
    }

    // 내 분야 변경
    @Transactional
    public void updateCategories(Long userId, List<Long> categoryIds) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 기존 매핑 제거
        userCategoryRepository.deleteByUserId(userId);
        userCategoryRepository.flush();

        // 존재하는 분야인지 검증
        List<Category> categories = categoryRepository.findAllById(categoryIds);
        if (categories.size() != categoryIds.size()) {
            throw new CustomException(ErrorCode.CATEGORY_NOT_FOUND);
        }

        // 새 매핑 생성
        user.setCategories(categories);
    }

    // 내 분야 조회
    public List<String> getCategories(Long userId) {
        return userCategoryRepository.findByUserIdWithCategory(userId)
                .stream()
                .map(uc -> uc.getCategory().getName())
                .toList();
    }

    // 내 기술스택 변경
    @Transactional
    public void updateSkills(Long userId, List<Long> skillIds) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 기존 매핑 제거
        userSkillRepository.deleteByUserId(userId);
        userSkillRepository.flush();

        // 존재하는 분야인지 검증
        List<Skill> skills = skillRepository.findAllById(skillIds);
        if (skills.size() != skillIds.size()) {
            throw new CustomException(ErrorCode.SKILL_NOT_FOUND);
        }

        user.setSkills(skills);
    }

    // 내 기술스택 조회
    public List<String> getSkills(Long userId) {
        return userSkillRepository.findByUserIdWithSkill(userId)
                .stream()
                .map(us -> us.getSkill().getName())
                .toList();
    }
}

