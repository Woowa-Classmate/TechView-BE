package com.interview.techview.dto.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UpdateUserCategoriesRequest {
    @NotEmpty(message = "{user.categories.required}")
    private List<Long> categories;
}
