package com.interview.techview.dto.category;

import com.interview.techview.domain.category.CategoryLevel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "분야 수정 요청 DTO")
@Getter
public class CategoryUpdateRequest {

    @Schema(
            description = "분야 이름",
            example = "서버 개발"
    )
    private String name;

    @Schema(
            description = "분야 레벨 (MAJOR/MEDIUM/MINOR 구분)",
            example = "MEDIUM"
    )
    private CategoryLevel level;
}

