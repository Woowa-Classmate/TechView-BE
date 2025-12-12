package com.interview.techview.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "내 기술스택 설정 요청 DTO")
@Getter
@NoArgsConstructor
public class UpdateUserSkillsRequest {

    @Schema(description = "기술(Skill) ID 목록", example = "[2, 5]")
    @NotEmpty(message = "{user.skills.required}")
    private List<Long> skills;
}
