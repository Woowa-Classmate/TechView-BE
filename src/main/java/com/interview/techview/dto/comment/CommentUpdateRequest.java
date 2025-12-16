package com.interview.techview.dto.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "댓글 수정 요청 DTO")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentUpdateRequest {

    @Schema(description = "댓글 내용", example = "수정된 댓글 내용")
    @NotBlank(message = "댓글 내용은 필수입니다.")
    private String content;

    @Schema(description = "댓글 비밀번호", example = "password123")
    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 6, max = 20, message = "비밀번호는 6자 이상 20자 이하로 작성해주세요.")
    private String password;
}

