package com.interview.techview.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "로그인 요청 DTO")
@Getter
@NoArgsConstructor
public class LoginRequest {

    @Schema(
            description = "이메일",
            example = "user@example.com"
    )
    @NotBlank(message = "{auth.email.required}")
    @Email(message = "{auth.email.invalid}")
    private String email;

    @Schema(
            description = "비밀번호",
            example = "Password!123"
    )
    @NotBlank(message = "{auth.password.required}")
    private String password;
}
