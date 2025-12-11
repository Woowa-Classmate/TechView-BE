package com.interview.techview.dto.question;

import com.interview.techview.domain.question.Difficulty;
import com.interview.techview.domain.question.Question;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class QuestionResponse {

    private Long id;
    private String question;
    private String answer;
    private Difficulty difficulty;

    private List<String> categories;
    private List<String> skills;

    public static QuestionResponse from(Question q) {
        List<String> categories = q.getCategories().stream()
                .map(qc -> qc.getCategory().getName())
                .toList();

        List<String> skills = q.getSkills().stream()
                .map(qs -> qs.getSkill().getName())
                .toList();

        return QuestionResponse.builder()
                .id(q.getId())
                .question(q.getQuestion())
                .answer(q.getAnswer())
                .difficulty(q.getDifficulty())
                .categories(categories)
                .skills(skills)
                .build();
    }
}
