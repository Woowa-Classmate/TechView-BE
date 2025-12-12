package com.interview.techview.dto.question;

import com.interview.techview.domain.question.Difficulty;
import com.interview.techview.domain.question.Question;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Schema(description = "질문 응답 DTO")
@Getter
@Builder
public class QuestionResponse {

    @Schema(description = "질문 ID", example = "10")
    private Long id;

    @Schema(description = "질문 내용")
    private String question;

    @Schema(description = "모범 답변")
    private String answer;

    @Schema(description = "질문 난이도")
    private Difficulty difficulty;


    @Schema(description = "분야 이름 목록", example = "[\"백엔드\", \"Spring\"]")
    private List<String> categories;

    @Schema(description = "기술 이름 목록", example = "[\"JPA\", \"Hibernate\"]")
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
