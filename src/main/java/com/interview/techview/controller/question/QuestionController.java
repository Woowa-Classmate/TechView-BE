package com.interview.techview.controller.question;

import com.interview.techview.domain.question.Difficulty;
import com.interview.techview.dto.common.ApiResponse;
import com.interview.techview.dto.question.QuestionCreateRequest;
import com.interview.techview.dto.question.QuestionResponse;
import com.interview.techview.dto.question.QuestionUpdateRequest;
import com.interview.techview.service.question.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    // 질문 생성
    @PostMapping
    public ResponseEntity<QuestionResponse> create(
            @Valid @RequestBody QuestionCreateRequest request
    ) {
        return ResponseEntity.ok(questionService.create(request));
    }

    // 질문 조회 (검색)
    @GetMapping
    public ResponseEntity<List<QuestionResponse>> search(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long skillId,
            @RequestParam(required = false) Difficulty difficulty,
            @RequestParam(required = false) String keyword
    ) {
        return ResponseEntity.ok(questionService.search(categoryId, skillId, difficulty, keyword));
    }

    // 질문 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponse> detail(@PathVariable Long id) {
        return ResponseEntity.ok(questionService.getDetail(id));
    }

    // 질문 수정
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody QuestionUpdateRequest request
    ) {
        questionService.update(id, request);
        return ResponseEntity.ok(ApiResponse.ok("질문이 수정되었습니다."));
    }

    // 질문 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        questionService.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("질문이 삭제되었습니다."));
    }

    // 추천
    @GetMapping("/recommendations")
    public ResponseEntity<List<QuestionResponse>> recommend(
            @RequestParam Difficulty difficulty,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long skillId
    ) {
        return ResponseEntity.ok(
                questionService.recommend(difficulty, categoryId, skillId)
        );
    }
}

