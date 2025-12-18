package com.interview.techview.controller.auth;

import com.interview.techview.dto.auth.LoginRequest;
import com.interview.techview.dto.auth.ResetPasswordRequest;
import com.interview.techview.dto.auth.SignUpRequest;
import com.interview.techview.dto.auth.TokenResponse;
import com.interview.techview.dto.common.ApiResponse;
import com.interview.techview.security.CustomUserDetails;
import com.interview.techview.service.auth.AuthService;
import com.interview.techview.swagger.AuthApi;
import com.interview.techview.swagger.PublicApi;
import com.interview.techview.util.CookieUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Auth",
        description = "인증 / 인가 관련 API"
)
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final CookieUtil cookieUtil;

    // 회원가입
    @Operation(
            summary = "회원가입",
            description = "이메일과 비밀번호를 이용해 회원가입을 진행합니다."
    )
    @PublicApi
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signUp(@Valid @RequestBody SignUpRequest request) {
        authService.signUp(request);
        return ResponseEntity.ok(ApiResponse.ok("회원가입이 완료되었습니다."));
    }

    // 로그인
    @Operation(
            summary = "로그인 (일반 사용자 & 관리자 통합)",
            description = """
                일반 사용자와 관리자 모두 이 API를 사용하여 로그인합니다.
                로그인 후 Access Token을 반환하며, Refresh Token은 HttpOnly Cookie로 저장됩니다.
                JWT에 포함된 role 정보(USER/ADMIN)에 따라 자동으로 권한이 구분됩니다.
                관리자 계정으로 로그인 시 /api/admin/** 경로에 접근할 수 있습니다.
            """
    )
    @PublicApi
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request) {

        TokenResponse tokens = authService.login(request);

        ResponseCookie cookie = cookieUtil.createRefreshTokenCookie(tokens.getRefreshToken());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new TokenResponse(tokens.getAccessToken(), null));
    }


    // 임시 비밀번호 발급
    @Operation(
            summary = "임시 비밀번호 발급",
            description = "로그인된 사용자에게 임시 비밀번호를 발급합니다."
    )
    @AuthApi
    @PatchMapping("/genPw")
    public ResponseEntity<ApiResponse> generateTempPassword(@AuthenticationPrincipal CustomUserDetails user) {
        String email = user.getUser().getEmail();
        String tempPw = authService.generateTempPassword(email);
        return ResponseEntity.ok(ApiResponse.ok("임시 비밀번호가 발급되었습니다.", tempPw));
    }

    // 비밀번호 변경
    @Operation(
            summary = "비밀번호 변경",
            description = "현재 비밀번호를 검증한 후 새 비밀번호로 변경합니다."
    )
    @AuthApi
    @PatchMapping("/resetPw")
    public ResponseEntity<ApiResponse> resetPassword(
            @AuthenticationPrincipal CustomUserDetails user,
            @Valid @RequestBody ResetPasswordRequest request
    ) {
        authService.resetPassword(user.getId(), request);
        return ResponseEntity.ok(ApiResponse.ok("비밀번호가 변경되었습니다."));
    }

    // 토큰 재발급
    @Operation(hidden = true)
    @PublicApi
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
    @Operation(
            summary = "로그아웃",
            description = "Refresh Token을 삭제하여 로그아웃 처리합니다."
    )
    @AuthApi
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout(@AuthenticationPrincipal CustomUserDetails user) {

        authService.logout(user.getId());

        ResponseCookie cookie = cookieUtil.deleteRefreshTokenCookie();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(ApiResponse.ok("로그아웃 되었습니다."));
    }
}
