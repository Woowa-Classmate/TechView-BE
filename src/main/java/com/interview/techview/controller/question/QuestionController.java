package com.interview.techview.controller.question;

import com.interview.techview.domain.question.Difficulty;
import com.interview.techview.dto.common.ApiResponse;
import com.interview.techview.dto.question.QuestionCreateRequest;
import com.interview.techview.dto.question.QuestionResponse;
import com.interview.techview.dto.question.QuestionUpdateRequest;
import com.interview.techview.service.question.QuestionService;
import com.interview.techview.swagger.AuthApi;
import com.interview.techview.swagger.PublicApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Question",
        description = "면접 질문 관리 API"
)
@RestController
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    // 질문 생성
    @Operation(
            summary = "질문 생성",
            description = "면접 질문을 생성하고 분야 및 기술을 매핑합니다."
    )
    @AuthApi
    @PostMapping
    public ResponseEntity<QuestionResponse> create(
            @Valid @RequestBody QuestionCreateRequest request
    ) {
        return ResponseEntity.ok(questionService.create(request));
    }

    // 질문 조회 (검색)
    @Operation(
            summary = "질문 검색",
            description = """
                조건에 따라 질문을 검색합니다.
                - categoryId: 분야 필터
                - skillId: 기술 필터
                - difficulty: 난이도
                - keyword: 질문 키워드
            """
    )
    @PublicApi
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
    @Operation(
            summary = "질문 상세 조회",
            description = "질문 ID로 단일 질문의 상세 정보를 조회합니다."
    )
    @PublicApi
    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponse> detail(@PathVariable Long id) {
        return ResponseEntity.ok(questionService.getDetail(id));
    }

    // 질문 수정
    @Operation(
            summary = "질문 수정",
            description = "질문 내용, 난이도, 분야, 기술 정보를 수정합니다."
    )
    @AuthApi
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody QuestionUpdateRequest request
    ) {
        questionService.update(id, request);
        return ResponseEntity.ok(ApiResponse.ok("질문이 수정되었습니다."));
    }

    // 질문 삭제
    @Operation(
            summary = "질문 삭제",
            description = "질문을 삭제합니다."
    )
    @AuthApi
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        questionService.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("질문이 삭제되었습니다."));
    }

    // 추천
    @Operation(
            summary = "질문 추천",
            description = "난이도 기반으로 질문을 추천합니다."
    )
    @PublicApi
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

