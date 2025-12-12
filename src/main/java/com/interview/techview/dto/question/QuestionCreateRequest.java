package com.interview.techview.dto.question;

import com.interview.techview.domain.question.Difficulty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Schema(description = "질문 생성 요청 DTO")
@Getter
public class QuestionCreateRequest {

    @Schema(description = "질문 내용", example = "Spring Bean의 생명주기를 설명하세요.")
    @NotBlank(message = "{question.content.required}")
    private String question;

    @Schema(description = "질문에 대한 모범 답변")
    @NotBlank(message = "{question.answer.required}")
    private String answer;

    @Schema(description = "질문 난이도", example = "MEDIUM")
    @NotNull(message = "{question.difficulty.required}")
    private Difficulty difficulty;

    @Schema(description = "분야(Category) ID 목록", example = "[1, 2]")
    @NotEmpty(message = "{question.category.required}")
    private List<Long> categories;

    @Schema(description = "기술(Skill) ID 목록", example = "[3, 5]")
    @NotEmpty(message = "{question.skill.required}")
    private List<Long> skills;
}
