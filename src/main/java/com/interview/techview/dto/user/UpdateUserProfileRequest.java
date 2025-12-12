package com.interview.techview.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "내 프로필 수정 요청 DTO")
@Getter
@NoArgsConstructor
public class UpdateUserProfileRequest {

    @Schema(description = "사용자 이름", example = "홍길동")
    @NotBlank(message = "{user.name.required}")
    private String name;
}

