package com.interview.techview.domain.question;

import com.interview.techview.domain.category.Category;
import com.interview.techview.domain.skill.Skill;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "questions")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @Column(nullable = false, length = 1000)
    private String question;

    @Lob
    private String answer;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Difficulty difficulty = Difficulty.MEDIUM;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Builder.Default
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionCategory> categories = new ArrayList<>();

    @Builder.Default
    @BatchSize(size = 50)
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionSkill> skills = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // update
    public void update(String question, String answer, Difficulty difficulty) {
        this.question = question;
        this.answer = answer;
        this.difficulty = difficulty;
    }

    public void setCategories(List<Category> categories) {
        this.categories.clear();
        categories.forEach(c -> this.categories.add(new QuestionCategory(this, c)));
    }

    public void setSkills(List<Skill> skills) {
        this.skills.clear();
        skills.forEach(s -> this.skills.add(new QuestionSkill(this, s)));
    }
}
