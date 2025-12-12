package com.interview.techview.dto.question;

import com.interview.techview.domain.question.Difficulty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import java.util.List;

@Schema(description = "질문 수정 요청 DTO")
@Getter
public class QuestionUpdateRequest {

    @Schema(description = "질문 내용")
    private String question;

    @Schema(description = "질문 답변")
    private String answer;

    @Schema(description = "질문 난이도")
    private Difficulty difficulty;


    @Schema(description = "분야 ID 목록")
    private List<Long> categories;

    @Schema(description = "기술 ID 목록")
    private List<Long> skills;
}
