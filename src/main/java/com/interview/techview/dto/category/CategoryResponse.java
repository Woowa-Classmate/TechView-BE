package com.interview.techview.dto.category;

import com.interview.techview.domain.category.Category;
import com.interview.techview.domain.category.CategoryLevel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "분야 응답 DTO")
@Getter
@Builder
public class CategoryResponse {

    @Schema(description = "분야 ID", example = "1")
    private Long id;

    @Schema(description = "분야 이름", example = "백엔드")
    private String name;

    @Schema(
            description = "분야 레벨 (MAJOR/MEDIUM/MINOR 구분)",
            example = "MEDIUM"
    )
    private CategoryLevel level;


    public static CategoryResponse from(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .level(category.getLevel())
                .build();
    }
}