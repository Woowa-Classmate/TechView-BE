package com.interview.techview.repository.question;

import com.interview.techview.domain.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository
        extends JpaRepository<Question, Long>, QuestionQueryRepository {
}