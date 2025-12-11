package com.interview.techview.repository.question;

import com.interview.techview.domain.question.QuestionSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionSkillRepository extends JpaRepository<QuestionSkill, Long> {
    List<QuestionSkill> findByQuestionId(Long questionId);
    void deleteByQuestionId(Long questionId);
}
