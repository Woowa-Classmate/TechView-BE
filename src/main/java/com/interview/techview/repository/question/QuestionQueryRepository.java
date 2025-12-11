package com.interview.techview.repository.question;

import com.interview.techview.domain.question.Difficulty;
import com.interview.techview.domain.question.Question;

import java.util.List;

public interface QuestionQueryRepository {
    List<Question> searchQuestions(Long categoryId, Long skillId, Difficulty difficulty, String keyword);
    List<Question> recommend(Difficulty difficulty, Long categoryId, Long skillId);
}
