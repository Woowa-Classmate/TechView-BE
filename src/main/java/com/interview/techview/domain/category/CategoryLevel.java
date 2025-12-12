package com.interview.techview.domain.category;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "분야(Category) 레벨")
public enum CategoryLevel {

    @Schema(description = "대분류 분야 (예: 백엔드, 프론트엔드)")
    MAJOR,

    @Schema(description = "중분류 분야 (예: Spring, React)")
    MEDIUM,

    @Schema(description = "소분류 분야 (예: JPA, 상태관리)")
    MINOR
}
