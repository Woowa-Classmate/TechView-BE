package com.interview.techview.repository.comment;

import com.interview.techview.domain.comment.Comment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @EntityGraph(attributePaths = {"user", "post"})
    List<Comment> findByPost_PostId(Long postId);

    @EntityGraph(attributePaths = {"user", "post"})
    @Override
    Optional<Comment> findById(Long id);
}

