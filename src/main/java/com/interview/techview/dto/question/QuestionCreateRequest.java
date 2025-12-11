package com.interview.techview.dto.question;

import com.interview.techview.domain.question.Difficulty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class QuestionCreateRequest {

    @NotBlank(message = "{question.content.required}")
    private String question;

    @NotBlank(message = "{question.answer.required}")
    private String answer;

    @NotNull(message = "{question.difficulty.required}")
    private Difficulty difficulty;

    @NotEmpty(message = "{question.category.required}")
    private List<Long> categories;

    @NotEmpty(message = "{question.skill.required}")
    private List<Long> skills;
}
