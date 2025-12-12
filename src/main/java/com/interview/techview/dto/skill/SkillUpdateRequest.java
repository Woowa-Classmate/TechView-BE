package com.interview.techview.dto.skill;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "기술스택 수정 요청 DTO")
@Getter
public class SkillUpdateRequest {

    @Schema(
            description = "기술스택 이름",
            example = "Spring Framework"
    )
    private String name;
}

