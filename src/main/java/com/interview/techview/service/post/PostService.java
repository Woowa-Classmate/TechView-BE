package com.interview.techview.service.post;

import com.interview.techview.domain.post.Post;
import com.interview.techview.domain.post.PostLike;
import com.interview.techview.domain.user.User;
import com.interview.techview.dto.post.PostCreateRequest;
import com.interview.techview.dto.post.PostResponse;
import com.interview.techview.dto.post.PostUpdateRequest;
import com.interview.techview.exception.CustomException;
import com.interview.techview.exception.ErrorCode;
import com.interview.techview.exception.post.PasswordMismatchException;
import com.interview.techview.exception.post.PostNotFoundException;
import com.interview.techview.repository.comment.CommentRepository;
import com.interview.techview.repository.post.PostBookmarkRepository;
import com.interview.techview.repository.post.PostLikeRepository;
import com.interview.techview.repository.post.PostRepository;
import com.interview.techview.repository.user.UserRepository;
import com.interview.techview.dto.common.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final PostBookmarkRepository postBookmarkRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 게시글 작성
    @Transactional
    public PostResponse save(PostCreateRequest dto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 공지사항 작성 시 ADMIN 권한 확인
        Boolean isNotice = dto.getIsNotice();
        if (isNotice != null && isNotice && user.getRole() != com.interview.techview.domain.user.Role.ADMIN) {
            throw new CustomException(ErrorCode.ADMIN_ACCESS_DENIED);
        }

        String hashedPassword = passwordEncoder.encode(dto.getPassword());
        Post savedPost = postRepository.save(dto.toEntity(user, hashedPassword));

        // 저장 후 EntityGraph를 사용하여 user를 함께 조회하여 Lazy Loading 문제 방지
        Post post = postRepository.findById(savedPost.getPostId())
                .orElseThrow(PostNotFoundException::new);

        return PostResponse.from(post);
    }

    // 공지사항 작성 - 관리자용 (비밀번호 자동 생성, isNotice = true)
    @Transactional
    public PostResponse createNotice(String title, String content, Long adminUserId) {
        User admin = userRepository.findById(adminUserId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 관리자 권한 확인
        if (admin.getRole() != com.interview.techview.domain.user.Role.ADMIN) {
            throw new CustomException(ErrorCode.ADMIN_ACCESS_DENIED);
        }

        // 공지사항은 기본 비밀번호 사용 (관리자가 삭제/수정할 수 있으므로)
        String defaultPassword = "admin123";
        String hashedPassword = passwordEncoder.encode(defaultPassword);

        Post notice = Post.builder()
                .title(title)
                .content(content)
                .user(admin)
                .password(hashedPassword)
                .isNotice(true) // 항상 공지사항으로 설정
                .build();

        Post savedNotice = postRepository.save(notice);

        // 저장 후 EntityGraph를 사용하여 user를 함께 조회하여 Lazy Loading 문제 방지
        Post post = postRepository.findById(savedNotice.getPostId())
                .orElseThrow(PostNotFoundException::new);

        return PostResponse.from(post);
    }

    // 게시글 전체 조회
    @Transactional(readOnly = true)
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    // 게시글 페이징 조회
    @Transactional(readOnly = true)
    public PageResponse<Post> findAll(Pageable pageable) {
        Page<Post> page = postRepository.findAll(pageable);
        return PageResponse.of(
                page.getContent(),
                page.getTotalElements(),
                page.getNumber(),
                page.getSize());
    }

    // 게시글 검색 (제목으로 검색)
    @Transactional(readOnly = true)
    public List<Post> searchByTitle(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return findAll();
        }
        return postRepository.searchByTitle(keyword);
    }

    // 게시글 단건 조회 (조회수 증가)
    @Transactional
    public Post findById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);

        // 조회수 증가
        post.incrementViewCount();

        return post;
    }

    // 게시글 단건 조회 - 관리자용 (조회수 증가 안 함)
    @Transactional(readOnly = true)
    public Post findByIdForAdmin(Long id) {
        return postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);
    }

    // 게시글 삭제
    @Transactional
    public void delete(Long id, String inputPassword) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);

        // 입력 받은 password와 게시글에 저장된 password를 비교
        if (!passwordEncoder.matches(inputPassword, post.getPassword())) {
            throw new PasswordMismatchException();
        }

        // 게시글 삭제 전에 관련 데이터 먼저 삭제 (외래키 제약조건 해결)
        // 1. 댓글 삭제
        commentRepository.deleteAll(commentRepository.findByPost_PostId(id));

        // 2. 좋아요 삭제
        postLikeRepository.deleteByPost_PostId(id);

        // 3. 북마크 삭제
        postBookmarkRepository.deleteByPost_PostId(id);

        // 4. 게시글 삭제
        postRepository.delete(post);
    }

    // 게시글 삭제 - 관리자용 (비밀번호 체크 안 함)
    @Transactional
    public void deleteForAdmin(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);

        // 게시글 삭제 전에 관련 데이터 먼저 삭제 (외래키 제약조건 해결)
        // 1. 댓글 삭제
        commentRepository.deleteAll(commentRepository.findByPost_PostId(id));

        // 2. 좋아요 삭제
        postLikeRepository.deleteByPost_PostId(id);

        // 3. 북마크 삭제
        postBookmarkRepository.deleteByPost_PostId(id);

        // 4. 게시글 삭제
        postRepository.delete(post);
    }

    // 게시글 수정
    @Transactional
    public PostResponse update(Long id, PostUpdateRequest dto) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);

        // 입력 받은 password와 게시글에 저장된 password를 비교
        if (!passwordEncoder.matches(dto.getPassword(), post.getPassword())) {
            throw new PasswordMismatchException();
        }

        post.update(dto.getTitle(), dto.getContent());
        return PostResponse.from(post);
    }

    // 게시글 수정 - 관리자용 (비밀번호 체크 안 함)
    @Transactional
    public PostResponse updateForAdmin(Long id, String title, String content) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);

        post.update(title, content);
        return PostResponse.from(post);
    }

    // 게시글 좋아요 추가/취소
    @Transactional
    public PostResponse toggleLike(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        boolean isLiked = postLikeRepository.existsByUser_IdAndPost_PostId(userId, postId);

        if (isLiked) {
            // 좋아요 취소
            postLikeRepository.deleteByUser_IdAndPost_PostId(userId, postId);
            post.decrementLikeCount();
        } else {
            // 좋아요 추가
            PostLike postLike = new PostLike(user, post);
            postLikeRepository.save(postLike);
            post.incrementLikeCount();
        }

        // 업데이트된 Post 조회
        Post updatedPost = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        return PostResponse.from(updatedPost);
    }
}
