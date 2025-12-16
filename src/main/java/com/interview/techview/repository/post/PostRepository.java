package com.interview.techview.repository.post;

import com.interview.techview.domain.post.Post;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>, PostQueryRepository {

    @EntityGraph(attributePaths = { "user" })
    @Override
    List<Post> findAll();

    @EntityGraph(attributePaths = { "user" })
    @Override
    Optional<Post> findById(Long id);
}
