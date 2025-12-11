package com.interview.techview.controller.skill;

import com.interview.techview.dto.common.ApiResponse;
import com.interview.techview.dto.skill.SkillCreateRequest;
import com.interview.techview.dto.skill.SkillResponse;
import com.interview.techview.dto.skill.SkillUpdateRequest;
import com.interview.techview.service.skill.SkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skills")
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;

    // 기술스택 생성
    @PostMapping
    public ResponseEntity<SkillResponse> createSkill(
            @Valid @RequestBody SkillCreateRequest request
    ) {
        return ResponseEntity.ok(skillService.createSkill(request));
    }

    // 기술스택 조회
    @GetMapping
    public ResponseEntity<List<SkillResponse>> getSkills() {
        return ResponseEntity.ok(skillService.getAllSkills());
    }

    // 기술스택 수정
    @PutMapping("/{id}")
    public ResponseEntity<SkillResponse> updateSkill(
            @PathVariable Long id,
            @Valid @RequestBody SkillUpdateRequest request
    ) {
        return ResponseEntity.ok(skillService.updateSkill(id, request));
    }

    // 기술스택 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteSkill(@PathVariable Long id) {
        skillService.deleteSkill(id);
        return ResponseEntity.ok(ApiResponse.ok("기술스택이 삭제되었습니다."));
    }
}

