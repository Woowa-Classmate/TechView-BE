package com.interview.techview.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "내 관심 분야 설정 요청 DTO")
@Getter
@NoArgsConstructor
public class UpdateUserCategoriesRequest {

    @Schema(description = "분야(Category) ID 목록", example = "[1, 3]")
    @NotEmpty(message = "{user.categories.required}")
    private List<Long> categories;
}
