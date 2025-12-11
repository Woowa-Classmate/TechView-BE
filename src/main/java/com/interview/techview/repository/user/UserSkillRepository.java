package com.interview.techview.repository.user;

import com.interview.techview.domain.user.UserCategory;
import com.interview.techview.domain.user.UserSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserSkillRepository extends JpaRepository<UserSkill, Long> {

    @Query("""
        SELECT us FROM UserSkill us
        JOIN FETCH us.skill
        WHERE us.user.id = :userId
    """)
    List<UserSkill> findByUserIdWithSkill(Long userId);

    List<UserSkill> findByUserId(Long userId);

    void deleteByUserId(Long userId);
}
