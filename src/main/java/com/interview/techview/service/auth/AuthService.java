package com.interview.techview.service.auth;

import com.interview.techview.domain.auth.RefreshToken;
import com.interview.techview.domain.user.Role;
import com.interview.techview.domain.user.User;
import com.interview.techview.dto.auth.ResetPasswordRequest;
import com.interview.techview.dto.auth.SignUpRequest;
import com.interview.techview.dto.auth.LoginRequest;
import com.interview.techview.dto.auth.TokenResponse;
import com.interview.techview.exception.CustomException;
import com.interview.techview.exception.ErrorCode;
import com.interview.techview.repository.auth.RefreshTokenRepository;
import com.interview.techview.repository.user.UserRepository;
import com.interview.techview.security.JwtTokenProvider;
import com.interview.techview.util.GenerateRandomPassword;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    public void signUp(SignUpRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }

        String encoded = passwordEncoder.encode(request.getPassword());

        User user = User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(encoded)
                .role(Role.USER)
                .build();

        userRepository.save(user);
    }

    // 로그인
    public TokenResponse login(LoginRequest request) {

        // 1) email로 유저 조회
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_CREDENTIAL));

        // 2) 비밀번호 비교
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_CREDENTIAL);
        }

        // 3) Access Token 생성
        String accessToken = jwtTokenProvider.createAccessToken(
                user.getId(),
                user.getRole().name()
        );

        // 4) Refresh Token 생성
        String refreshToken = jwtTokenProvider.createRefreshToken(
                user.getId()
        );

        // 5) 기존 refreshToken 삭제 후 새로 저장 (RTR)
        refreshTokenRepository.findByUserId(user.getId())
                .ifPresent(token -> {
                    refreshTokenRepository.delete(token);
                    refreshTokenRepository.flush();
                });

        LocalDateTime expiry = jwtTokenProvider.getRefreshExpiryDate();

        RefreshToken newRefresh = RefreshToken.builder()
                .user(user)
                .token(refreshToken)
                .expiryDate(expiry)
                .build();

        refreshTokenRepository.save(newRefresh);

        // 6) 응답 반환
        return new TokenResponse(accessToken, refreshToken);
    }

    // 임시 비밀번호 발급
    public String generateTempPassword(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        String tempPassword = GenerateRandomPassword.generate(10);

        user.updatePassword(passwordEncoder.encode(tempPassword));

        // TODO: 이메일 전송 로직 (MailService)
        
        // TODO: 이메일 전송 로직 구현 시 반환 제거
        return tempPassword;
    }

    // 비밀번호 변경
    public void resetPassword(Long userId, ResetPasswordRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        user.updatePassword(passwordEncoder.encode(request.getNewPassword()));
    }

    // 토큰 재발급
    public TokenResponse refresh(String refreshToken) {

        // 1) 토큰이 유효한지 체크
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        // 2) refreshToken에서 userId 추출
        Long userId = jwtTokenProvider.getUserId(refreshToken);

        // 3) DB에서 해당 user의 refreshToken 조회
        RefreshToken stored = refreshTokenRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.UNAUTHORIZED));

        // 4) DB에 저장된 refresh token과 일치하는지 확인
        if (!stored.getToken().equals(refreshToken)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        // 5) AccessToken 재발급
        User user = stored.getUser();
        String newAccess = jwtTokenProvider.createAccessToken(
                user.getId(),
                user.getRole().name()
        );

        // 6) RefreshToken도 새로 교체(RTR)
        String newRefresh = jwtTokenProvider.createRefreshToken(user.getId());
        LocalDateTime expiry = jwtTokenProvider.getRefreshExpiryDate();

        stored.updateToken(newRefresh, expiry);
        refreshTokenRepository.save(stored);

        // 7) 응답 반환
        return new TokenResponse(newAccess, newRefresh);
    }

    // 로그아웃
    public void logout(Long userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }
}

