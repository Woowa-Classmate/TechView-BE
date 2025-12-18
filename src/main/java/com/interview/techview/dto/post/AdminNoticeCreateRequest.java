package com.interview.techview.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "관리자 공지사항 생성 요청 DTO")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminNoticeCreateRequest {

    @Schema(description = "공지사항 제목", example = "시스템 점검 안내")
    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 200, message = "제목은 200자 이하로 작성해주세요.")
    private String title;

    @Schema(description = "공지사항 내용", example = "시스템 점검으로 인한 서비스 일시 중단 안내입니다.")
    @NotBlank(message = "내용은 필수입니다.")
    private String content;
}


