package com.interview.techview.dto.category;

import com.interview.techview.domain.category.CategoryLevel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
@Schema(description = "분야 생성 요청 DTO")
@Getter
public class CategoryCreateRequest {

    @Schema(
            description = "분야 이름",
            example = "백엔드"
    )
    @NotBlank(message = "{category.name.required}")
    private String name;

    @Schema(
            description = "분야 레벨 (MAJOR/MEDIUM/MINOR 구분)",
            example = "MAJOR"
    )
    @NotNull(message = "{category.level.required}")
    private CategoryLevel level;
}

