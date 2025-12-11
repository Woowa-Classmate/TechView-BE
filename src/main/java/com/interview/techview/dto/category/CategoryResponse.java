package com.interview.techview.dto.category;

import com.interview.techview.domain.category.Category;
import com.interview.techview.domain.category.CategoryLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryResponse {
    private Long id;
    private String name;
    private CategoryLevel level;

    public static CategoryResponse from(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .level(category.getLevel())
                .build();
    }
}