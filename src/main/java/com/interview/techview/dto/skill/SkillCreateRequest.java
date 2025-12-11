package com.interview.techview.dto.skill;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SkillCreateRequest {

    @NotBlank(message = "{skill.name.required}")
    private String name;
}

