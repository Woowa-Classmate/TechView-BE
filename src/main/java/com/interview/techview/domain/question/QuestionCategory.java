package com.interview.techview.domain.question;

import com.interview.techview.domain.category.Category;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "question_categories",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_question_category",
                        columnNames = {"question_id", "category_id"}
                )
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class QuestionCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_category_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id")
    private Category category;

    public QuestionCategory(Question question, Category category) {
        this.question = question;
        this.category = category;
    }
}
