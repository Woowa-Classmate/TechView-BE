package com.interview.techview.dto.report;

import com.interview.techview.domain.report.ReportQuestion;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReportQuestionResponse {

    @Schema(example = "1")
    private Long id;

    @Schema(description = "질문 내용")
    private String question;

    @Schema(description = "영상 링크")
    private String videoUrl;

    @Schema(description = "영상 답변")
    private String stt;

    @Schema(description = "분석 결과")
    private String analysis;

    public static ReportQuestionResponse from(ReportQuestion rq) {
        return ReportQuestionResponse.builder()
                .id(rq.getId())
                .question(rq.getQuestion().getQuestion())
                .videoUrl(rq.getVideoUrl())
                .stt(rq.getSttReply())
                .analysis(rq.getAnalysis())
                .build();
    }
}
