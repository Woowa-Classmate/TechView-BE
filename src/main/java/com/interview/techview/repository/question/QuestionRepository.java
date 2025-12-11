package com.interview.techview.repository.question;

import com.interview.techview.domain.question.Question;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionRepository
        extends JpaRepository<Question, Long>, QuestionQueryRepository {

    @EntityGraph(attributePaths = {"categories.category", "skills.skill"})
    Optional<Question> findById(Long id);
}