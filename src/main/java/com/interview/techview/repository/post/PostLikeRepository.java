package com.interview.techview.repository.post;

import com.interview.techview.domain.post.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByUser_IdAndPost_PostId(Long userId, Long postId);
    boolean existsByUser_IdAndPost_PostId(Long userId, Long postId);
    void deleteByUser_IdAndPost_PostId(Long userId, Long postId);
    void deleteByPost_PostId(Long postId); // 게시글 삭제 시 모든 좋아요 삭제
}

