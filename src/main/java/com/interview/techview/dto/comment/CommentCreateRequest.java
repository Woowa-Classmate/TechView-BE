package com.interview.techview.dto.comment;

import com.interview.techview.domain.comment.Comment;
import com.interview.techview.domain.post.Post;
import com.interview.techview.domain.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "댓글 생성 요청 DTO")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommentCreateRequest {

    @Schema(description = "댓글 내용", example = "좋은 글 감사합니다!")
    @NotBlank(message = "댓글 내용은 필수입니다.")
    private String content;

    @Schema(description = "게시글 ID", example = "1")
    @NotNull(message = "게시글 ID는 필수입니다.")
    private Long postId;

    @Schema(description = "댓글 비밀번호", example = "password123")
    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 6, max = 20, message = "비밀번호는 6자 이상 20자 이하로 작성해주세요.")
    private String password;

    public Comment toEntity(Post post, User user, String hashedPassword) {
        return Comment.builder()
                .content(content)
                .post(post)
                .user(user)
                .password(hashedPassword)
                .build();
    }
}

