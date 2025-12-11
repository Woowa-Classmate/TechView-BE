package com.interview.techview.dto.skill;

import com.interview.techview.domain.skill.Skill;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SkillResponse {
    private Long id;
    private String name;

    public static SkillResponse from(Skill skill) {
        return SkillResponse.builder()
                .id(skill.getId())
                .name(skill.getName())
                .build();
    }
}
