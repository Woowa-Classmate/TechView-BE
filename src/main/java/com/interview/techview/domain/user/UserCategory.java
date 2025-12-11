package com.interview.techview.domain.user;

import com.interview.techview.domain.category.Category;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "user_categories",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_user_category",
                        columnNames = {"user_id", "category_id"}
                )
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_category_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id")
    private Category category;

    public UserCategory(User user, Category category) {
        this.user = user;
        this.category = category;
    }
}
