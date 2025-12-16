package com.interview.techview.service.post;

import com.interview.techview.domain.post.Post;
import com.interview.techview.domain.post.PostBookmark;
import com.interview.techview.domain.user.User;
import com.interview.techview.dto.post.PostListResponse;
import com.interview.techview.exception.CustomException;
import com.interview.techview.exception.ErrorCode;
import com.interview.techview.exception.post.PostNotFoundException;
import com.interview.techview.repository.post.PostBookmarkRepository;
import com.interview.techview.repository.post.PostRepository;
import com.interview.techview.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostBookmarkService {

    private final PostBookmarkRepository postBookmarkRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 북마크 추가/삭제
    @Transactional
    public void toggleBookmark(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        boolean isBookmarked = postBookmarkRepository.existsByUser_IdAndPost_PostId(userId, postId);

        if (isBookmarked) {
            // 북마크 삭제
            postBookmarkRepository.deleteByUser_IdAndPost_PostId(userId, postId);
        } else {
            // 북마크 추가
            PostBookmark bookmark = new PostBookmark(user, post);
            postBookmarkRepository.save(bookmark);
        }
    }

    // 사용자의 북마크 목록 조회
    @Transactional(readOnly = true)
    public List<PostListResponse> getBookmarks(Long userId) {
        List<PostBookmark> bookmarks = postBookmarkRepository.findByUser_Id(userId);
        return bookmarks.stream()
                .map(bookmark -> PostListResponse.from(bookmark.getPost()))
                .toList();
    }

    // 북마크 여부 확인
    @Transactional(readOnly = true)
    public boolean isBookmarked(Long postId, Long userId) {
        return postBookmarkRepository.existsByUser_IdAndPost_PostId(userId, postId);
    }
}

