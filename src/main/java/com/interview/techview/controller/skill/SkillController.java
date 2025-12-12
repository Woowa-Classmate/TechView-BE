package com.interview.techview.controller.skill;

import com.interview.techview.dto.common.ApiResponse;
import com.interview.techview.dto.skill.SkillCreateRequest;
import com.interview.techview.dto.skill.SkillResponse;
import com.interview.techview.dto.skill.SkillUpdateRequest;
import com.interview.techview.service.skill.SkillService;
import com.interview.techview.swagger.AuthApi;
import com.interview.techview.swagger.PublicApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Skill",
        description = "면접 질문에 사용되는 기술스택 관리 API"
)
@RestController
@RequestMapping("/skills")
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;

    // 기술스택 생성
    @Operation(
            summary = "기술스택 생성",
            description = "새로운 기술스택을 등록합니다."
    )
    @AuthApi
    @PostMapping
    public ResponseEntity<SkillResponse> createSkill(
            @Valid @RequestBody SkillCreateRequest request
    ) {
        return ResponseEntity.ok(skillService.createSkill(request));
    }

    // 기술스택 조회
    @Operation(
            summary = "기술스택 목록 조회",
            description = "등록된 모든 기술스택 목록을 조회합니다."
    )
    @PublicApi
    @GetMapping
    public ResponseEntity<List<SkillResponse>> getSkills() {
        return ResponseEntity.ok(skillService.getAllSkills());
    }

    // 기술스택 수정
    @Operation(
            summary = "기술스택 수정",
            description = "기술스택 이름을 수정합니다."
    )
    @AuthApi
    @PutMapping("/{id}")
    public ResponseEntity<SkillResponse> updateSkill(
            @PathVariable Long id,
            @Valid @RequestBody SkillUpdateRequest request
    ) {
        return ResponseEntity.ok(skillService.updateSkill(id, request));
    }

    // 기술스택 삭제
    @Operation(
            summary = "기술스택 삭제",
            description = "기술스택을 삭제합니다."
    )
    @AuthApi
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteSkill(@PathVariable Long id) {
        skillService.deleteSkill(id);
        return ResponseEntity.ok(ApiResponse.ok("기술스택이 삭제되었습니다."));
    }
}

