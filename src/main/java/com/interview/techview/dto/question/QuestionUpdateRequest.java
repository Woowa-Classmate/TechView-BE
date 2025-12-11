package com.interview.techview.dto.question;

import com.interview.techview.domain.question.Difficulty;
import lombok.Getter;
import java.util.List;

@Getter
public class QuestionUpdateRequest {
    private String question;
    private String answer;
    private Difficulty difficulty;

    private List<Long> categories;
    private List<Long> skills;
}
