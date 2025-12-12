package com.interview.techview.domain.question;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "질문 난이도")
public enum Difficulty {

    @Schema(description = "쉬움 (기초 개념 위주)")
    EASY,

    @Schema(description = "보통 (실무/응용)")
    MEDIUM,

    @Schema(description = "어려움 (심화/아키텍처)")
    HARD
}
