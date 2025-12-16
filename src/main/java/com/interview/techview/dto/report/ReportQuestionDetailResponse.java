package com.interview.techview.dto.report;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "리포트 질문 답변 상세 조회 DTO")
@Getter
@Builder
public class ReportQuestionDetailResponse {

    @Schema(example = "1")
    private Long questionId;

    @Schema(example = "자바의 GC에 대해 설명해보세요.")
    private String question;

    @Schema(
            description = "답변 영상 URL",
            example = "http://localhost/videos/1/1.mp4",
            nullable = true
    )
    private String videoUrl;

    @Schema(
            description = "STT 분석 결과",
            example = "GC는 메모리를 자동으로 관리하는 기능입니다.",
            nullable = true
    )
    private String stt;

    @Schema(
            description = "AI 분석 결과",
            example = "개념 이해는 명확하나 예시가 부족합니다.",
            nullable = true
    )
    private String analysis;
}
