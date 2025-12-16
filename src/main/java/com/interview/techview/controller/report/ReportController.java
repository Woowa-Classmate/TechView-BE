package com.interview.techview.controller.report;

import com.interview.techview.dto.common.ApiResponse;
import com.interview.techview.dto.report.ReportCreateRequest;
import com.interview.techview.dto.report.ReportResponse;
import com.interview.techview.security.CustomUserDetails;
import com.interview.techview.service.report.ReportService;
import com.interview.techview.swagger.AuthApi;
import com.interview.techview.swagger.PublicApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(
        name = "Report",
        description = "면접 리포트 및 답변 관리 API"
)
@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ReportController {

    private final ReportService reportService;

    @Tag(
            name = "Report",
            description = "면접 리포트 및 답변 관리 API"
    )
    @AuthApi
    @PostMapping
    public ResponseEntity<ReportResponse> create(
            @AuthenticationPrincipal CustomUserDetails user,
            @Valid @RequestBody ReportCreateRequest request
    ) {
        return ResponseEntity.ok(reportService.create(user.getId(), request));
    }

    // 리포트 질문 조회
    @Operation(
            summary = "리포트 질문 조회",
            description = "리포트에 포함된 질문과 답변 상태를 조회합니다."
    )
    @AuthApi
    @GetMapping("/{reportId}/questions")
    public ResponseEntity<ReportResponse> questions(@PathVariable Long reportId) {
        return ResponseEntity.ok(reportService.getReport(reportId));
    }

    // 인터뷰 영상 조회
    @Operation(
            summary = "인터뷰 영상 조회",
            description = "질문에 업로드된 답변 영상 URL을 조회합니다."
    )
    @PublicApi
    @GetMapping("/{reportId}/questions/{questionId}/video")
    public ResponseEntity<String> getVideo(
            @PathVariable Long reportId,
            @PathVariable Long questionId
    ) {
        return ResponseEntity.ok(
                reportService.getVideoUrl(reportId, questionId)
        );
    }

    // 인터뷰 영상 업로드
    @Operation(
            summary = "인터뷰 영상 업로드",
            description = "질문에 대한 답변 영상을 업로드합니다."
    )
    @AuthApi
    @PostMapping("/{reportId}/questions/{questionId}/video")
    public ResponseEntity<ApiResponse> uploadVideo(
            @PathVariable Long reportId,
            @PathVariable Long questionId,
            @RequestPart MultipartFile file
    ) {
        reportService.uploadVideo(reportId, questionId, file);
        return ResponseEntity.ok(ApiResponse.ok("영상이 업로드되었습니다."));
    }

    // STT 결과 조회
    @Operation(
            summary = "STT 결과 조회",
            description = "답변 영상에 대한 STT 분석 결과를 조회합니다."
    )
    @PublicApi
    @GetMapping("/{reportId}/questions/{questionId}/stt")
    public ResponseEntity<String> getStt(
            @PathVariable Long reportId,
            @PathVariable Long questionId
    ) {
        return ResponseEntity.ok(
                reportService.getSttResult(reportId, questionId)
        );
    }

    // STT 요청
    @Operation(
            summary = "STT 분석 요청",
            description = "업로드된 답변 영상에 대해 음성 → 텍스트 변환을 요청합니다."
    )
    @AuthApi
    @PostMapping("/{reportId}/questions/{questionId}/stt")
    public ResponseEntity<ApiResponse> stt(
            @PathVariable Long reportId,
            @PathVariable Long questionId
    ) {
        reportService.requestStt(reportId, questionId);
        return ResponseEntity.ok(ApiResponse.ok("STT 요청이 완료되었습니다."));
    }

    // AI 분석 결과 조회
    @Operation(
            summary = "AI 분석 결과 조회",
            description = "답변에 대한 AI 분석 결과를 조회합니다."
    )
    @PublicApi
    @GetMapping("/{reportId}/questions/{questionId}/analysis")
    public ResponseEntity<String> getAnalysis(
            @PathVariable Long reportId,
            @PathVariable Long questionId
    ) {
        return ResponseEntity.ok(
                reportService.getAnalysisResult(reportId, questionId)
        );
    }

    // AI 분석 요청
    @Operation(
            summary = "AI 답변 분석 요청",
            description = "STT 결과를 기반으로 AI 답변 분석을 요청합니다."
    )
    @AuthApi
    @PostMapping("/{reportId}/questions/{questionId}/analysis")
    public ResponseEntity<ApiResponse> analysis(
            @PathVariable Long reportId,
            @PathVariable Long questionId
    ) {
        reportService.requestAnalysis(reportId, questionId);
        return ResponseEntity.ok(ApiResponse.ok("AI 분석이 완료되었습니다."));
    }
}

