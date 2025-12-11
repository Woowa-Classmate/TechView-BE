package com.interview.techview.dto.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UpdateUserSkillsRequest {
    @NotEmpty(message = "{user.skills.required}")
    private List<Long> skills;
}
