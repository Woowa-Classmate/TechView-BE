package com.interview.techview.controller.admin;

import com.interview.techview.domain.question.Difficulty;
import com.interview.techview.dto.common.ApiResponse;
import com.interview.techview.dto.question.QuestionCreateRequest;
import com.interview.techview.dto.question.QuestionResponse;
import com.interview.techview.dto.question.QuestionUpdateRequest;
import com.interview.techview.service.question.QuestionService;
import com.interview.techview.swagger.AuthApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Admin", description = "관리자 API")
@RestController
@RequestMapping("/admin/missions")
@RequiredArgsConstructor
public class AdminMissionController {

    private final QuestionService questionService;

    // 모든 미션 조회 (관리자용)
    @Operation(summary = "모든 미션 조회", description = "모든 면접 미션을 조회합니다. (관리자 권한 필요)")
    @AuthApi
    @GetMapping
    public ResponseEntity<List<QuestionResponse>> getAllMissions() {
        return ResponseEntity.ok(questionService.getAll());
    }

    // 미션 검색 (관리자용)
    @Operation(
        summary = "미션 검색",
        description = """
            조건에 따라 미션을 검색합니다. (관리자 권한 필요)
            - categoryId: 분야 필터
            - skillId: 기술 필터
            - difficulty: 난이도 (EASY, MEDIUM, HARD)
            - keyword: 질문 키워드
            """
    )
    @AuthApi
    @GetMapping("/search")
    public ResponseEntity<List<QuestionResponse>> searchMissions(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long skillId,
            @RequestParam(required = false) Difficulty difficulty,
            @RequestParam(required = false) String keyword
    ) {
        return ResponseEntity.ok(questionService.search(categoryId, skillId, difficulty, keyword));
    }

    // 미션 상세 조회 (관리자용)
    @Operation(summary = "미션 상세 조회", description = "미션 ID로 단일 미션의 상세 정보를 조회합니다. (관리자 권한 필요)")
    @AuthApi
    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponse> getMissionDetail(@PathVariable Long id) {
        return ResponseEntity.ok(questionService.getDetail(id));
    }

    // 미션 추가 (관리자용)
    @Operation(summary = "미션 추가", description = "새로운 면접 미션을 생성합니다. (관리자 권한 필요)")
    @AuthApi
    @PostMapping
    public ResponseEntity<QuestionResponse> createMission(
            @Valid @RequestBody QuestionCreateRequest request) {
        QuestionResponse createdMission = questionService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMission);
    }

    // 미션 수정 (관리자용)
    @Operation(summary = "미션 수정", description = "미션 내용, 난이도, 분야, 기술 정보를 수정합니다. (관리자 권한 필요)")
    @AuthApi
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateMission(
            @PathVariable Long id,
            @Valid @RequestBody QuestionUpdateRequest request) {
        questionService.update(id, request);
        return ResponseEntity.ok(ApiResponse.ok("미션이 수정되었습니다."));
    }

    // 미션 삭제 (관리자용)
    @Operation(summary = "미션 삭제", description = "미션을 삭제합니다. (관리자 권한 필요)")
    @AuthApi
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteMission(@PathVariable Long id) {
        questionService.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("미션이 삭제되었습니다."));
    }
}


