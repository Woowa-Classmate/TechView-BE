package com.interview.techview.controller.comment;

import com.interview.techview.domain.comment.Comment;
import com.interview.techview.dto.comment.CommentCreateRequest;
import com.interview.techview.dto.comment.CommentListResponse;
import com.interview.techview.dto.comment.CommentPasswordRequest;
import com.interview.techview.dto.comment.CommentResponse;
import com.interview.techview.dto.comment.CommentUpdateRequest;
import com.interview.techview.security.CustomUserDetails;
import com.interview.techview.service.comment.CommentService;
import com.interview.techview.swagger.AuthApi;
import com.interview.techview.swagger.PublicApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Comment", description = "댓글 관리 API")
@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성 API
    @Operation(summary = "댓글 작성", description = "게시글에 댓글을 작성합니다. (인증 필요)")
    @AuthApi
    @PostMapping
    public ResponseEntity<CommentResponse> addComment(
            @Valid @RequestBody CommentCreateRequest dto,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        CommentResponse comment = commentService.save(dto, userDetails.getId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(comment);
    }

    // 게시글의 댓글 목록 조회 API
    @Operation(summary = "댓글 목록 조회", description = "특정 게시글의 모든 댓글을 조회합니다.")
    @PublicApi
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentListResponse>> getCommentsByPostId(@PathVariable Long postId) {
        List<CommentListResponse> commentListDto = commentService.findByPostId(postId)
                .stream()
                .map(CommentListResponse::from)
                .toList();
        return ResponseEntity.ok(commentListDto);
    }

    // 댓글 단건 조회 API
    @Operation(summary = "댓글 단건 조회", description = "댓글 ID로 단일 댓글의 상세 정보를 조회합니다.")
    @PublicApi
    @GetMapping("/{id}")
    public ResponseEntity<CommentResponse> getCommentById(@PathVariable Long id) {
        Comment comment = commentService.findById(id);
        return ResponseEntity.ok(CommentResponse.from(comment));
    }

    // 댓글 삭제 API
    @Operation(summary = "댓글 삭제", description = "비밀번호를 확인하여 댓글을 삭제합니다.")
    @PublicApi
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommentById(
            @PathVariable Long id,
            @Valid @RequestBody CommentPasswordRequest dto) {
        commentService.delete(id, dto.getPassword());
        return ResponseEntity.ok().build();
    }

    // 댓글 수정 API
    @Operation(summary = "댓글 수정", description = "비밀번호를 확인하여 댓글을 수정합니다.")
    @PublicApi
    @PutMapping("/{id}")
    public ResponseEntity<CommentResponse> updateCommentById(
            @PathVariable Long id,
            @Valid @RequestBody CommentUpdateRequest dto) {
        CommentResponse comment = commentService.update(id, dto);
        return ResponseEntity.ok(comment);
    }
}

