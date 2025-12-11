package com.interview.techview.repository.question;

import com.interview.techview.domain.question.QuestionCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionCategoryRepository extends JpaRepository<QuestionCategory, Long> {
    List<QuestionCategory> findByQuestionId(Long questionId);
    void deleteByQuestionId(Long questionId);
}
