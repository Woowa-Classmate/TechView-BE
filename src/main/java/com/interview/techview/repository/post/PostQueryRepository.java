package com.interview.techview.repository.post;

import com.interview.techview.domain.post.Post;

import java.util.List;

public interface PostQueryRepository {
    // 예시: 제목으로 게시글 검색
    List<Post> searchByTitle(String keyword);

    // 예시: 특정 사용자의 게시글 조회
    List<Post> findByUserId(Long userId);
}

