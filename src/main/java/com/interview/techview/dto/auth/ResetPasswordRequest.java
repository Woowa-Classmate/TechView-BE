package com.interview.techview.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Schema(description = "비밀번호 변경 요청 DTO")
@Getter
public class ResetPasswordRequest {

    @Schema(
            description = "현재 비밀번호",
            example = "OldPassword!123"
    )
    @NotBlank(message = "{auth.password.required}")
    private String currentPassword;

    @Schema(
            description = "새 비밀번호",
            example = "NewPassword!123"
    )
    @NotBlank(message = "{auth.password.new.required}")
    private String newPassword;
}