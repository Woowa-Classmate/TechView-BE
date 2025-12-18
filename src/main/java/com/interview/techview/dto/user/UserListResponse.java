package com.interview.techview.dto.user;

import com.interview.techview.domain.user.Role;
import com.interview.techview.domain.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "사용자 목록 응답 DTO")
@Getter
@Builder
public class UserListResponse {

    @Schema(description = "사용자 ID", example = "1")
    private Long userId;

    @Schema(description = "이메일", example = "user@example.com")
    private String email;

    @Schema(description = "이름", example = "홍길동")
    private String name;

    @Schema(description = "권한", example = "USER")
    private Role role;

    public static UserListResponse from(User user) {
        return UserListResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole())
                .build();
    }
}
