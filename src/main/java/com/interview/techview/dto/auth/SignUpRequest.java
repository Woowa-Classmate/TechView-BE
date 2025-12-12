package com.interview.techview.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "회원가입 요청 DTO")
@Getter
@NoArgsConstructor
public class SignUpRequest {

    @Schema(
            description = "이메일",
            example = "user@example.com"
    )
    @NotBlank(message = "{auth.email.required}")
    @Email(message = "{auth.email.invalid}")
    private String email;

    @Schema(
            description = "이름",
            example = "홍길동"
    )
    @NotBlank(message = "{auth.name.required}")
    private String name;

    @Schema(
            description = "비밀번호 (8~20자, 영문+숫자+특수문자)",
            example = "Password!123"
    )
    @NotBlank(message = "{auth.password.required}")
    @Size(min = 8, max = 20, message = "{auth.password.length}")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+=-]).+$",
            message = "{auth.password.invalid}"
    )
    private String password;
}
