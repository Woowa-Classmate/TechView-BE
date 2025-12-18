package com.interview.techview.domain.post;

import com.interview.techview.domain.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "Post")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키를 자동으로 1씩 증가
    @Column(name = "post_id", updatable = false)
    private Long postId;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Lob // Large Object 긴 문자열에 사용
    @Column(name = "content", nullable = false, columnDefinition = "MEDIUMTEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder.Default
    @Column(name = "like_count", nullable = false)
    private int likeCount = 0; // 기본 좋아요 개수는 0개

    @Builder.Default
    @Column(name = "view_count", nullable = false)
    private int viewCount = 0; // 기본 조회수는 0개

    @Builder.Default
    @Column(name = "is_notice", nullable = false)
    private boolean isNotice = false; // 공지사항 여부 (기본값: false)

    @Column(name = "password", nullable = false)
    private String password;

    @CreatedDate // 엔티티가 처음 저장될 때의 시간 자동 기록
    @Column(name = "create_at", updatable = false)
    private LocalDateTime createAt;

    @LastModifiedDate // 엔티티가 수정될 때의 시간 자동 기록
    @Column(name = "updated_at")
    private LocalDateTime updateAt;

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void incrementLikeCount() {
        this.likeCount++;
    }

    public void decrementLikeCount() {
        if (this.likeCount > 0) {
            this.likeCount--;
        }
    }

    public void incrementViewCount() {
        this.viewCount++;
    }
}
