package com.interview.techview.dto.user;

import com.interview.techview.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class UserProfileResponse {

    private Long id;
    private String email;
    private String name;
    private LocalDateTime createdAt;

    private List<String> categories;
    private List<String> skills;

    public static UserProfileResponse from(User user, List<String> categories, List<String> skills) {
        return UserProfileResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .createdAt(user.getCreatedAt())
                .categories(categories)
                .skills(skills)
                .build();
    }
}
