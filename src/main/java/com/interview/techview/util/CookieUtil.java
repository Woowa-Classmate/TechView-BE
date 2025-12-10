package com.interview.techview.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {

    @Value("${jwt.refresh-expiration}")
    private long refreshMaxAge;

    private static final String REFRESH_TOKEN_NAME = "refreshToken";

    // refresh token 생성
    public ResponseCookie createRefreshTokenCookie(String token) {
        return ResponseCookie.from(REFRESH_TOKEN_NAME, token)
                .httpOnly(true)
                .secure(true)
                .path("/api/auth")
                .sameSite("Strict")
                .maxAge(refreshMaxAge)
                .build();
    }

    // refresh token 삭제(로그아웃)
    public ResponseCookie deleteRefreshTokenCookie() {
        return ResponseCookie.from(REFRESH_TOKEN_NAME, "")
                .httpOnly(true)
                .secure(true)
                .path("/auth")
                .sameSite("Strict")
                .maxAge(0)  // 만료
                .build();
    }
}
