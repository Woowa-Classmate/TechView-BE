package com.interview.techview.domain.question;

import com.interview.techview.domain.skill.Skill;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "question_skills",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_question_skill",
                        columnNames = {"question_id", "skill_id"}
                )
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class QuestionSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_skill_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "skill_id")
    private Skill skill;
}
