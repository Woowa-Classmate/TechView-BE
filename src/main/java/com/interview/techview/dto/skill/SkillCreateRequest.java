package com.interview.techview.dto.skill;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Schema(description = "기술스택 생성 요청 DTO")
@Getter
public class SkillCreateRequest {

    @Schema(
            description = "기술스택 이름",
            example = "Spring Boot"
    )
    @NotBlank(message = "{skill.name.required}")
    private String name;
}

