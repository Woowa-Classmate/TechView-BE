package com.interview.techview.service.auth;

import com.interview.techview.domain.auth.RefreshToken;
import com.interview.techview.domain.user.Role;
import com.interview.techview.domain.user.User;
import com.interview.techview.dto.auth.LoginRequest;
import com.interview.techview.dto.auth.ResetPasswordRequest;
import com.interview.techview.dto.auth.SignUpRequest;
import com.interview.techview.dto.auth.TokenResponse;
import com.interview.techview.exception.CustomException;
import com.interview.techview.exception.ErrorCode;
import com.interview.techview.repository.auth.RefreshTokenRepository;
import com.interview.techview.repository.user.UserRepository;
import com.interview.techview.security.JwtTokenProvider;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @InjectMocks  // @Mock으로 만든 의존성을 주입받은 "실제 객체" (테스트 대상 객체)
    private AuthService authService;

    @Mock  // AuthService가 의존하는 객체의 "가짜 구현체(mock)" (실제 DB, 외부 시스템을 타지 않도록 대체한다)
    private UserRepository userRepository;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Nested
    class SignUpTest {
        // 헬퍼 메서드
        private SignUpRequest signUpRequest() {
            SignUpRequest request = new SignUpRequest();
            ReflectionTestUtils.setField(request, "email", "test@test.com");
            ReflectionTestUtils.setField(request, "name", "테스트");
            ReflectionTestUtils.setField(request, "password", "Password1!");
            return request;
        }

        @Test
        @DisplayName("회원가입 성공 - 정상 요청이면 유저를 저장한다")
        void signup_success_when_valid_request() {
            // given
            SignUpRequest request = signUpRequest();

            given(userRepository.findByEmail("test@test.com"))
                    .willReturn(Optional.empty());

            given(passwordEncoder.encode(any()))
                    .willReturn("encodedPassword");

            // when
            authService.signUp(request);

            // then
            // verify : “행동(사이드 이펙트)”을 검증할 때 사용 (결과값/예외 => assert 사용)
            verify(userRepository).save(any());  // save가 호출되었는지 검증 (기본적으로 1회 호출을 기대)
        }

        @Test
        @DisplayName("회원가입 실패 - 이미 존재하는 이메일이면 예외가 발생한다")
        void signup_fail_when_email_already_exists() {
            // given
            SignUpRequest request = signUpRequest();

            given(userRepository.findByEmail("test@test.com"))
                    .willReturn(Optional.of(mock(User.class)));

            // when & then
            assertThatThrownBy(() -> authService.signUp(request))
                    .isInstanceOf(CustomException.class)
                    .hasMessageContaining("이미 사용 중인 이메일");

            verify(userRepository, never()).save(any());  // save가 호출되지 않았는지 검증
        }
    }

    @Nested
    class LoginTest {
        // 헬퍼 메서드
        private LoginRequest loginRequest(String email, String password) {
            LoginRequest request = new LoginRequest();
            ReflectionTestUtils.setField(request, "email", email);
            ReflectionTestUtils.setField(request, "password", password);
            return request;
        }

        @Test
        @DisplayName("로그인 성공 - 올바른 이메일과 비밀번호면 토큰을 반환한다")
        void login_success_when_valid_credentials() {
            // given
            LoginRequest request = loginRequest("test@test.com", "password");

            User user = User.builder()
                    .id(1L)
                    .email("test@test.com")
                    .password("encoded")  // passwordEncoder로 해시화된 비밀번호가 들어감
                    .role(Role.USER)
                    .build();

            given(userRepository.findByEmail("test@test.com"))
                    .willReturn(Optional.of(user));

            given(passwordEncoder.matches("password", "encoded"))
                    .willReturn(true);

            given(jwtTokenProvider.createAccessToken(1L, "USER"))
                    .willReturn("access-token");

            given(jwtTokenProvider.createRefreshToken(1L))
                    .willReturn("refresh-token");

            // when
            TokenResponse response = authService.login(request);

            // then
            // assertThat~ : 결과/예외 등 반환값이 있을 때 사용 (That : 결과 상태 검증 | ThatThrownBy : 예외 발생 여부 검증)
            assertThat(response.getAccessToken()).isEqualTo("access-token");
        }

        @Test
        @DisplayName("로그인 실패 - 존재하지 않는 이메일이면 예외가 발생한다")
        void login_fail_when_email_not_found() {
            // given
            LoginRequest request = loginRequest("no@test.com", "password");

            given(userRepository.findByEmail("no@test.com"))
                    .willReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> authService.login(request))
                    .isInstanceOf(CustomException.class)
                    .hasMessage(ErrorCode.INVALID_CREDENTIAL.getMessage());
        }

        @Test
        @DisplayName("로그인 실패 - 비밀번호가 틀리면 예외가 발생한다")
        void login_fail_when_password_invalid() {
            // given
            LoginRequest request = loginRequest("test@test.com", "wrong");

            User user = User.builder()
                    .email("test@test.com")
                    .password("encoded")
                    .build();

            given(userRepository.findByEmail("test@test.com"))
                    .willReturn(Optional.of(user));

            given(passwordEncoder.matches("wrong", "encoded"))
                    .willReturn(false);

            // when & then
            assertThatThrownBy(() -> authService.login(request))
                    .isInstanceOf(CustomException.class)
                    .hasMessage(ErrorCode.INVALID_CREDENTIAL.getMessage());
        }
    }

    @Nested
    class ResetPasswordTest {
        // 헬퍼 메서드
        private User resetPasswordUser(Long id) {
            return User.builder()
                    .id(id)
                    .password("encoded-old")  // passwordEncoder로 해시화된 비밀번호가 들어감
                    .build();
        }

        @Test
        @DisplayName("비밀번호 변경 성공 - 현재 비밀번호가 일치하면 변경된다")
        void reset_password_success_when_current_password_matches() {
            // given
            Long userId = 1L;

            ResetPasswordRequest request = new ResetPasswordRequest();
            ReflectionTestUtils.setField(request, "currentPassword", "old");
            ReflectionTestUtils.setField(request, "newPassword", "new");

            User user = resetPasswordUser(userId);

            given(userRepository.findById(userId))
                    .willReturn(Optional.of(user));

            given(passwordEncoder.matches("old", "encoded-old"))
                    .willReturn(true);

            given(passwordEncoder.encode("new"))
                    .willReturn("encoded-new");

            // when
            authService.resetPassword(userId, request);

            // then
            assertThat(user.getPassword()).isEqualTo("encoded-new");
        }

        @Test
        @DisplayName("비밀번호 변경 실패 - 유저가 없으면 예외 발생")
        void reset_password_fail_when_user_not_found() {
            // given
            given(userRepository.findById(1L))
                    .willReturn(Optional.empty());

            ResetPasswordRequest request = new ResetPasswordRequest();

            // when & then
            assertThatThrownBy(() -> authService.resetPassword(1L, request))
                    .isInstanceOf(CustomException.class)
                    .hasMessage(ErrorCode.USER_NOT_FOUND.getMessage());
        }

        @Test
        @DisplayName("비밀번호 변경 실패 - 현재 비밀번호가 틀리면 예외 발생")
        void reset_password_fail_when_current_password_invalid() {
            // given
            Long userId = 1L;

            ResetPasswordRequest request = new ResetPasswordRequest();
            ReflectionTestUtils.setField(request, "currentPassword", "wrong");

            User user = resetPasswordUser(userId);

            given(userRepository.findById(userId))
                    .willReturn(Optional.of(user));

            given(passwordEncoder.matches("wrong", "encoded-old"))
                    .willReturn(false);

            // when & then
            assertThatThrownBy(() -> authService.resetPassword(userId, request))
                    .isInstanceOf(CustomException.class)
                    .hasMessage(ErrorCode.INVALID_PASSWORD.getMessage());
        }
    }

    @Nested
    class RefreshTest {
        // 헬퍼 메서드
        private RefreshToken refreshStoredToken(User user, String refreshToken) {
            return RefreshToken.builder()
                    .user(user)
                    .token(refreshToken)
                    .build();
        }

        @Test
        @DisplayName("토큰 재발급 성공 - 유효한 refreshToken이면 새로운 accessToken을 반환한다")
        void refresh_success_when_valid_refresh_token() {
            // given
            String refreshToken = "valid-refresh-token";

            User user = User.builder()
                    .id(1L)
                    .role(Role.USER)
                    .build();

            RefreshToken storedToken = refreshStoredToken(user, refreshToken);

            given(jwtTokenProvider.getUserId(refreshToken))
                    .willReturn(1L);

            given(refreshTokenRepository.findByUserId(1L))
                    .willReturn(Optional.of(storedToken));

            given(jwtTokenProvider.createAccessToken(1L, "USER"))
                    .willReturn("new-access-token");

            given(jwtTokenProvider.createRefreshToken(1L))
                    .willReturn("new-refresh-token");

            // when
            TokenResponse response = authService.refresh(refreshToken);

            // then
            assertThat(response.getAccessToken()).isEqualTo("new-access-token");
        }

        @Test
        @DisplayName("토큰 재발급 실패 - refreshToken이 유효하지 않으면 예외 발생")
        void refresh_fail_when_token_invalid() {
            // given
            String refreshToken = "invalid-token";

            doThrow(new JwtException("invalid refresh token"))
                    .when(jwtTokenProvider)
                    .validateRefreshToken(refreshToken);

            // when & then
            assertThatThrownBy(() -> authService.refresh(refreshToken))
                    .isInstanceOf(CustomException.class)
                    .hasMessage(ErrorCode.UNAUTHORIZED.getMessage());
        }

        @Test
        @DisplayName("토큰 재발급 실패 - DB의 refreshToken과 다르면 예외 발생")
        void refresh_fail_when_token_not_match_db() {
            // given
            String requestToken = "request-token";
            String storedTokenValue = "stored-token";

            User user = User.builder()
                    .id(1L)
                    .build();

            RefreshToken storedToken = refreshStoredToken(user, storedTokenValue);

            given(jwtTokenProvider.getUserId(requestToken))
                    .willReturn(1L);

            given(refreshTokenRepository.findByUserId(1L))
                    .willReturn(Optional.of(storedToken));

            // when & then
            assertThatThrownBy(() -> authService.refresh(requestToken))
                    .isInstanceOf(CustomException.class)
                    .hasMessage(ErrorCode.UNAUTHORIZED.getMessage());
        }
    }
}
