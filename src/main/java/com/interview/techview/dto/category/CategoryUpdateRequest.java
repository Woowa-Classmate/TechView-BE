package com.interview.techview.dto.category;

import com.interview.techview.domain.category.CategoryLevel;
import lombok.Getter;

@Getter
public class CategoryUpdateRequest {
    private String name;
    private CategoryLevel level;
}

