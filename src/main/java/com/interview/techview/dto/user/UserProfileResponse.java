package com.interview.techview.dto.user;

import com.interview.techview.domain.user.Role;
import com.interview.techview.domain.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "사용자 프로필 응답 DTO")
@Getter
@Builder
public class UserProfileResponse {

    @Schema(description = "사용자 ID", example = "1")
    private Long id;

    @Schema(description = "이메일")
    private String email;

    @Schema(description = "사용자 이름")
    private String name;

    @Schema(description = "권한", example = "USER")
    private Role role;

    @Schema(description = "가입 일시")
    private LocalDateTime createdAt;


    @Schema(description = "설정된 분야 목록", example = "[\"백엔드\", \"프론트엔드\"]")
    private List<String> categories;

    @Schema(description = "설정된 기술스택 목록", example = "[\"Spring\", \"JPA\"]")
    private List<String> skills;


    public static UserProfileResponse from(User user) {

        List<String> categories = user.getCategories().stream()
                .map(uc -> uc.getCategory().getName())
                .toList();

        List<String> skills = user.getSkills().stream()
                .map(us -> us.getSkill().getName())
                .toList();

        return UserProfileResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .categories(categories)
                .skills(skills)
                .build();
    }
}
