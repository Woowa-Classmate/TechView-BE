package com.interview.techview.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "게시글 비밀번호 확인 요청 DTO")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostPasswordRequest {

    @Schema(description = "게시글 비밀번호", example = "password123")
    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 6, max = 20, message = "비밀번호는 6자 이상 20자 이하로 작성해주세요.")
    private String password;
}
