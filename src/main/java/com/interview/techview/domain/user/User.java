package com.interview.techview.domain.user;

import com.interview.techview.domain.category.Category;
import com.interview.techview.domain.skill.Skill;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // update
    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void setCategories(List<Category> categories) {
        this.categories.clear();
        categories.forEach(c -> this.categories.add(new UserCategory(this, c)));
    }

    public void setSkills(List<Skill> skills) {
        this.skills.clear();
        skills.forEach(s -> this.skills.add(new UserSkill(this, s)));
    }
}
