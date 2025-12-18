package com.interview.techview.domain.user;

import com.interview.techview.domain.category.Category;
import com.interview.techview.domain.skill.Skill;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, length = 255)
    private String password;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role = Role.USER;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserCategory> categories = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserSkill> skills = new ArrayList<>();

    // 계정 잠금 관련 필드
    @Builder.Default
    @Column(nullable = false)
    private int failedAttempts = 0; // 로그인 실패 횟수

    @Column(name = "locked_until")
    private LocalDateTime lockedUntil; // 계정 잠금 해제 시간

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private LockLevel lockLevel = LockLevel.NONE; // 현재 잠금 레벨

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // update
    public void updateEmail(String email) {
        this.email = email;
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateRole(Role role) {
        this.role = role;
    }

    public void setCategories(List<Category> categories) {
        this.categories.clear();
        categories.forEach(c -> this.categories.add(new UserCategory(this, c)));
    }

    public void setSkills(List<Skill> skills) {
        this.skills.clear();
        skills.forEach(s -> this.skills.add(new UserSkill(this, s)));
    }

    // 계정 잠금 관련 메서드
    public void incrementFailedAttempts() {
        this.failedAttempts++;
    }

    public void resetFailedAttempts() {
        this.failedAttempts = 0;
        this.lockedUntil = null;
        this.lockLevel = LockLevel.NONE;
    }

    public void setLockedUntil(LocalDateTime lockedUntil) {
        this.lockedUntil = lockedUntil;
    }

    public void setLockLevel(LockLevel lockLevel) {
        this.lockLevel = lockLevel;
    }

    public boolean isLocked() {
        if (lockLevel == LockLevel.NONE) {
            return false;
        }
        if (lockLevel == LockLevel.PERMANENT) {
            return true;
        }
        // 임시 잠금인 경우 시간 확인
        return lockedUntil != null && LocalDateTime.now().isBefore(lockedUntil);
    }
}
