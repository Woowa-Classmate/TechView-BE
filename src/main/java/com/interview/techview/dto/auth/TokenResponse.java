package com.interview.techview.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "JWT 토큰 응답 DTO")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {
    @Schema(
            description = "Access Token (Authorization 헤더에 사용)",
            example = "eyJhbGciOiJIUzI1NiJ9..."
    )
    private String accessToken;

    @Schema(
            description = "Refresh Token (HttpOnly Cookie 저장)",
            example = "eyJhbGciOiJIUzI1NiJ9..."
    )
    private String refreshToken;
}

