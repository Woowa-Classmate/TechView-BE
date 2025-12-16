package com.interview.techview.repository.post;

import com.interview.techview.domain.post.PostBookmark;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostBookmarkRepository extends JpaRepository<PostBookmark, Long> {
    @EntityGraph(attributePaths = {"post.user"})
    List<PostBookmark> findByUser_Id(Long userId);
    
    Optional<PostBookmark> findByUser_IdAndPost_PostId(Long userId, Long postId);
    boolean existsByUser_IdAndPost_PostId(Long userId, Long postId);
    void deleteByUser_IdAndPost_PostId(Long userId, Long postId);
    void deleteByPost_PostId(Long postId); // 게시글 삭제 시 모든 북마크 삭제
}

