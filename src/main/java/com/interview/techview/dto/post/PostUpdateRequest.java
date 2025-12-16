package com.interview.techview.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "게시글 수정 요청 DTO")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostUpdateRequest {

    @Schema(description = "게시글 제목", example = "수정된 제목")
    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 200, message = "제목은 200자 이하로 작성해주세요.")
    private String title;

    @Schema(description = "게시글 내용", example = "수정된 내용")
    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    @Schema(description = "게시글 비밀번호", example = "password123")
    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 6, max = 20, message = "비밀번호는 6자 이상 20자 이하로 작성해주세요.")
    private String password;
}

