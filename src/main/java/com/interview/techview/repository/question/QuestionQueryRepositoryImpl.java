package com.interview.techview.repository.question;

import com.interview.techview.domain.question.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class QuestionQueryRepositoryImpl implements QuestionQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public List<Question> searchQuestions(Long categoryId, Long skillId, Difficulty difficulty, String keyword) {

        QQuestion q = QQuestion.question1;
        QQuestionCategory qc = QQuestionCategory.questionCategory;
        QQuestionSkill qs = QQuestionSkill.questionSkill;

        return query
                .selectDistinct(q)
                .from(q)
                .leftJoin(q.categories, qc).fetchJoin()
                .leftJoin(q.skills, qs)
                .where(
                        categoryId != null ? qc.category.id.eq(categoryId) : null,
                        skillId != null ? qs.skill.id.eq(skillId) : null,
                        difficulty != null ? q.difficulty.eq(difficulty) : null,
                        keyword != null ? q.question.contains(keyword) : null
                )
                .fetch();
    }

    @Override
    public List<Question> recommend(Difficulty difficulty, Long categoryId, Long skillId) {

        QQuestion q = QQuestion.question1;
        QQuestionCategory qc = QQuestionCategory.questionCategory;
        QQuestionSkill qs = QQuestionSkill.questionSkill;

        return query
                .selectDistinct(q)
                .from(q)
                .leftJoin(q.categories, qc).fetchJoin()
                .leftJoin(q.skills, qs)
                .where(
                        q.difficulty.eq(difficulty),
                        categoryId != null ? qc.category.id.eq(categoryId) : null,
                        skillId != null ? qs.skill.id.eq(skillId) : null
                )
                .fetch();
    }
}

