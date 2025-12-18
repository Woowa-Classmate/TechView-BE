package com.interview.techview.dto.user;

import com.interview.techview.domain.user.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "관리자 사용자 생성 요청 DTO")
@Getter
@NoArgsConstructor
public class AdminUserCreateRequest {

    @Schema(description = "이메일", example = "user@example.com")
    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @Schema(description = "이름", example = "홍길동")
    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    @Schema(description = "비밀번호 (8~20자, 영문+숫자+특수문자)", example = "Password!123")
    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 작성해주세요.")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+=-]).+$",
            message = "비밀번호는 영문, 숫자, 특수문자를 포함해야 합니다."
    )
    private String password;

    @Schema(description = "권한", example = "USER")
    @NotNull(message = "권한은 필수입니다.")
    private Role role;
}


