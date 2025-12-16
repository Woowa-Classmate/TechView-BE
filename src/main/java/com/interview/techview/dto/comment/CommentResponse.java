package com.interview.techview.dto.comment;

import com.interview.techview.domain.comment.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Schema(description = "댓글 응답 DTO")
@Getter
@Builder
public class CommentResponse {

    @Schema(description = "댓글 ID", example = "1")
    private Long commentId;

    @Schema(description = "댓글 내용")
    private String content;

    @Schema(description = "게시글 ID", example = "1")
    private Long postId;

    @Schema(description = "작성자 이름")
    private String userName;

    @Schema(description = "생성일시")
    private LocalDateTime createAt;

    @Schema(description = "수정일시")
    private LocalDateTime updateAt;

    public static CommentResponse from(Comment comment) {
        if (comment == null) {
            throw new IllegalArgumentException("Comment cannot be null");
        }

        String userName = comment.getUser() != null ? comment.getUser().getName() : "Unknown";
        Long postId = comment.getPost() != null ? comment.getPost().getPostId() : null;

        return CommentResponse.builder()
                .commentId(comment.getCommentId())
                .content(comment.getContent())
                .postId(postId)
                .userName(userName)
                .createAt(comment.getCreateAt())
                .updateAt(comment.getUpdateAt())
                .build();
    }
}

