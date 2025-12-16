package com.interview.techview.service.comment;

import com.interview.techview.domain.comment.Comment;
import com.interview.techview.domain.post.Post;
import com.interview.techview.domain.user.User;
import com.interview.techview.dto.comment.CommentCreateRequest;
import com.interview.techview.dto.comment.CommentResponse;
import com.interview.techview.dto.comment.CommentUpdateRequest;
import com.interview.techview.exception.CustomException;
import com.interview.techview.exception.ErrorCode;
import com.interview.techview.exception.comment.CommentNotFoundException;
import com.interview.techview.exception.post.PasswordMismatchException;
import com.interview.techview.exception.post.PostNotFoundException;
import com.interview.techview.repository.comment.CommentRepository;
import com.interview.techview.repository.post.PostRepository;
import com.interview.techview.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 댓글 작성
    @Transactional
    public CommentResponse save(CommentCreateRequest dto, Long userId) {
        Post post = postRepository.findById(dto.getPostId())
                .orElseThrow(PostNotFoundException::new);
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        
        String hashedPassword = passwordEncoder.encode(dto.getPassword());
        Comment savedComment = commentRepository.save(dto.toEntity(post, user, hashedPassword));
        
        // 저장 후 EntityGraph를 사용하여 user와 post를 함께 조회
        Comment comment = commentRepository.findById(savedComment.getCommentId())
                .orElseThrow(CommentNotFoundException::new);
        
        return CommentResponse.from(comment);
    }

    // 게시글의 댓글 목록 조회
    @Transactional(readOnly = true)
    public List<Comment> findByPostId(Long postId) {
        return commentRepository.findByPost_PostId(postId);
    }

    // 댓글 단건 조회
    @Transactional(readOnly = true)
    public Comment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(CommentNotFoundException::new);
    }

    // 댓글 삭제
    @Transactional
    public void delete(Long id, String inputPassword) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(CommentNotFoundException::new);

        if (!passwordEncoder.matches(inputPassword, comment.getPassword())) {
            throw new PasswordMismatchException();
        }

        commentRepository.delete(comment);
    }

    // 댓글 수정
    @Transactional
    public CommentResponse update(Long id, CommentUpdateRequest dto) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(CommentNotFoundException::new);

        if (!passwordEncoder.matches(dto.getPassword(), comment.getPassword())) {
            throw new PasswordMismatchException();
        }

        comment.update(dto.getContent());
        return CommentResponse.from(comment);
    }
}

