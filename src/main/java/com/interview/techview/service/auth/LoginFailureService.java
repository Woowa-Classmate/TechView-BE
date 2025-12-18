package com.interview.techview.service.auth;

import com.interview.techview.domain.user.LockLevel;
import com.interview.techview.domain.user.User;
import com.interview.techview.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 로그인 실패 처리 전담 서비스
 * REQUIRES_NEW 트랜잭션 전파 속성을 사용하여 부모 트랜잭션의 롤백과 독립적으로
 * 실패 횟수 및 잠금 상태를 업데이트합니다.
 */
@Service
@RequiredArgsConstructor
public class LoginFailureService {

    private final UserRepository userRepository;

    private static final int MAX_FAILED_ATTEMPTS_5MIN = 3;  // 3회 실패 시 5분 잠금
    private static final int MAX_FAILED_ATTEMPTS_10MIN = 5; // 5회 실패 시 10분 잠금
    private static final int MAX_FAILED_ATTEMPTS_PERMANENT = 7; // 7회 실패 시 영구 잠금

    /**
     * 로그인 실패 처리
     * 실패 횟수를 증가시키고, 실패 횟수에 따라 계정을 잠급니다.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleLoginFailure(User user) {
        user.incrementFailedAttempts();
        int failedAttempts = user.getFailedAttempts();

        if (failedAttempts >= MAX_FAILED_ATTEMPTS_PERMANENT) {
            // 7회 이상 실패 시 영구 잠금
            user.setLockLevel(LockLevel.PERMANENT);
            user.setLockedUntil(null);
        } else if (failedAttempts >= MAX_FAILED_ATTEMPTS_10MIN) {
            // 5회 이상 실패 시 10분 잠금
            user.setLockLevel(LockLevel.TEMPORARY_10MIN);
            user.setLockedUntil(LocalDateTime.now().plusMinutes(10));
        } else if (failedAttempts >= MAX_FAILED_ATTEMPTS_5MIN) {
            // 3회 이상 실패 시 5분 잠금
            user.setLockLevel(LockLevel.TEMPORARY_5MIN);
            user.setLockedUntil(LocalDateTime.now().plusMinutes(5));
        }

        userRepository.save(user);
    }

    /**
     * 로그인 성공 시 실패 횟수 초기화
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleLoginSuccess(User user) {
        user.resetFailedAttempts();
        userRepository.save(user);
    }
}


