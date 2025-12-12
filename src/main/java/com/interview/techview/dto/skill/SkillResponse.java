package com.interview.techview.dto.skill;

import com.interview.techview.domain.skill.Skill;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "기술스택 응답 DTO")
@Getter
@Builder
public class SkillResponse {

    @Schema(description = "기술스택 ID", example = "5")
    private Long id;

    @Schema(description = "기술스택 이름", example = "Spring Boot")
    private String name;


    public static SkillResponse from(Skill skill) {
        return SkillResponse.builder()
                .id(skill.getId())
                .name(skill.getName())
                .build();
    }
}
