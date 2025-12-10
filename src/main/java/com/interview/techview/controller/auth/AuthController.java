package com.interview.techview.controller.auth;

import com.interview.techview.dto.auth.LoginRequest;
import com.interview.techview.dto.auth.SignUpRequest;
import com.interview.techview.dto.auth.TokenResponse;
import com.interview.techview.dto.common.ApiResponse;
import com.interview.techview.security.CustomUserDetails;
import com.interview.techview.service.auth.AuthService;
import com.interview.techview.util.CookieUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final CookieUtil cookieUtil;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signUp(@Valid @RequestBody SignUpRequest request) {
        authService.signUp(request);
        return ResponseEntity.ok(ApiResponse.ok("회원가입이 완료되었습니다."));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request) {

        TokenResponse tokens = authService.login(request);

        ResponseCookie cookie = cookieUtil.createRefreshTokenCookie(tokens.getRefreshToken());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new TokenResponse(tokens.getAccessToken(), null));
    }

    // 토큰 재발급
    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@CookieValue("refreshToken") String refreshToken) {

        TokenResponse tokens = authService.refresh(refreshToken);

        // 새 refreshToken을 쿠키로 저장
        ResponseCookie cookie = cookieUtil.createRefreshTokenCookie(tokens.getRefreshToken());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new TokenResponse(tokens.getAccessToken(), null));
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout(@AuthenticationPrincipal CustomUserDetails user) {

        authService.logout(user.getId());

        ResponseCookie cookie = cookieUtil.deleteRefreshTokenCookie();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(ApiResponse.ok("로그아웃 되었습니다."));
    }
}
