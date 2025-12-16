package com.interview.techview.dto.report;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "리포트 응답 DTO")
@Getter
@Builder
public class ReportResponse {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "2025-01-01T12:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "질문 및 답변 목록")
    private List<ReportQuestionResponse> questions;
}

