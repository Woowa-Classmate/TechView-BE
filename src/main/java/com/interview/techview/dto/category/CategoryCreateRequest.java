package com.interview.techview.dto.category;

import com.interview.techview.domain.category.CategoryLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CategoryCreateRequest {
    @NotBlank(message = "{category.name.required}")
    private String name;

    @NotNull(message = "{category.level.required}")
    private CategoryLevel level;
}

