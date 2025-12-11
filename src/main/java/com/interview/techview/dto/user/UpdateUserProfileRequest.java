package com.interview.techview.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateUserProfileRequest {
    @NotBlank(message = "{user.name.required}")
    private String name;
}

