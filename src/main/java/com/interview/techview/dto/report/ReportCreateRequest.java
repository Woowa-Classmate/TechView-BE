package com.interview.techview.dto.report;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.util.List;

@Schema(description = "리포트 생성 요청 DTO")
@Getter
public class ReportCreateRequest {

    @Schema(
            description = "리포트에 포함할 질문 ID 목록",
            example = "[1, 2, 3, 4, 5]"
    )
    @NotEmpty(message = "{report.questions.required}")
    private List<Long> questionIds;
}
