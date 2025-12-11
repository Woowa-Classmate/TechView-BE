package com.interview.techview.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ResetPasswordRequest {

    @NotBlank(message = "{auth.password.required}")
    private String currentPassword;

    @NotBlank(message = "{auth.password.new.required}")
    private String newPassword;
}