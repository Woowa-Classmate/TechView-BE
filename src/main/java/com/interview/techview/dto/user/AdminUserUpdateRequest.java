package com.interview.techview.dto.user;

import com.interview.techview.domain.user.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "관리자 사용자 수정 요청 DTO")
@Getter
@NoArgsConstructor
public class AdminUserUpdateRequest {

    @Schema(description = "이메일", example = "user@example.com")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @Schema(description = "이름", example = "홍길동")
    private String name;

    @Schema(description = "권한", example = "USER")
    private Role role;
}

