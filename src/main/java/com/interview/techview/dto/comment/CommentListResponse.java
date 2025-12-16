package com.interview.techview.dto.comment;

import com.interview.techview.domain.comment.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Schema(description = "댓글 목록 응답 DTO")
@Getter
@Builder
public class CommentListResponse {

    @Schema(description = "댓글 ID", example = "1")
    private Long commentId;

    @Schema(description = "댓글 내용")
    private String content;

    @Schema(description = "작성자 이름")
    private String userName;

    @Schema(description = "생성일시")
    private LocalDateTime createAt;

    public static CommentListResponse from(Comment comment) {
        if (comment == null) {
            throw new IllegalArgumentException("Comment cannot be null");
        }

        String userName = comment.getUser() != null ? comment.getUser().getName() : "Unknown";

        return CommentListResponse.builder()
                .commentId(comment.getCommentId())
                .content(comment.getContent())
                .userName(userName)
                .createAt(comment.getCreateAt())
                .build();
    }
}

