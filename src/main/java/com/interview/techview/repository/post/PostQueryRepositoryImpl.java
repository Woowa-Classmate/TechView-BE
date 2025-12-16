package com.interview.techview.repository.post;

import com.interview.techview.domain.post.Post;
import com.interview.techview.domain.post.QPost;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PostQueryRepositoryImpl implements PostQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Post> searchByTitle(String keyword) {
        QPost post = QPost.post;

        return queryFactory
                .selectFrom(post)
                .leftJoin(post.user).fetchJoin() // user를 함께 조회
                .where(post.title.contains(keyword))
                .orderBy(post.createAt.desc())
                .fetch();
    }

    @Override
    public List<Post> findByUserId(Long userId) {
        QPost post = QPost.post;

        return queryFactory
                .selectFrom(post)
                .leftJoin(post.user).fetchJoin() // user를 함께 조회
                .where(post.user.id.eq(userId))
                .orderBy(post.createAt.desc())
                .fetch();
    }
}

