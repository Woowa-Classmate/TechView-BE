package com.interview.techview.repository.question;

import com.interview.techview.domain.question.Difficulty;
import com.interview.techview.domain.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("""
        SELECT DISTINCT q FROM Question q
        LEFT JOIN QuestionCategory qc ON q.id = qc.question.id
        LEFT JOIN Category c ON qc.category.id = c.id
        LEFT JOIN QuestionSkill qs ON q.id = qs.question.id
        LEFT JOIN Skill s ON qs.skill.id = s.id
        WHERE (:categoryId IS NULL OR c.id = :categoryId)
          AND (:skillId IS NULL OR s.id = :skillId)
          AND (:difficulty IS NULL OR q.difficulty = :difficulty)
          AND (:keyword IS NULL OR q.question LIKE %:keyword%)
    """)
    List<Question> searchQuestions(
            @Param("categoryId") Long categoryId,
            @Param("skillId") Long skillId,
            @Param("difficulty") Difficulty difficulty,
            @Param("keyword") String keyword
    );

    // 추천 로직 (난이도 기반)
    @Query("""
        SELECT DISTINCT q FROM Question q
        WHERE q.difficulty = :difficulty
        AND (:categoryId IS NULL OR EXISTS (
            SELECT 1 FROM QuestionCategory qc 
            WHERE qc.question = q AND qc.category.id = :categoryId
        ))
        AND (:skillId IS NULL OR EXISTS (
            SELECT 1 FROM QuestionSkill qs 
            WHERE qs.question = q AND qs.skill.id = :skillId
        ))
    """)
    List<Question> recommend(
            @Param("difficulty") Difficulty difficulty,
            @Param("categoryId") Long categoryId,
            @Param("skillId") Long skillId
    );
}


