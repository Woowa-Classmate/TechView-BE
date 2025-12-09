package com.interview.techview.repository.skill;

import com.interview.techview.domain.skill.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {
}
