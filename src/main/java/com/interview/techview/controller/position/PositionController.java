package com.interview.techview.controller.position;

import com.interview.techview.dto.question.QuestionResponse;
import com.interview.techview.dto.common.PageResponse;
import com.interview.techview.service.question.QuestionService;
import com.interview.techview.swagger.PublicApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Position",
        description = "포지션별 면접 질문 조회 API"
)
@RestController
@RequestMapping("/position")
@RequiredArgsConstructor
public class PositionController {

    private final QuestionService questionService;

    @Operation(
            summary = "포지션별 질문 조회",
            description = """
                포지션 이름으로 해당 포지션의 면접 질문을 페이징하여 조회합니다. 기본 페이지 크기는 10입니다.
                - difficultyOrder: 난이도 정렬 (asc: 낮은 순, desc: 높은 순). 기본값은 asc입니다.
                - 예: Frontend, Backend, Android, iOS, DevOps
            """
    )
    @PublicApi
    @GetMapping("/{positionName}")
    public ResponseEntity<PageResponse<QuestionResponse>> getQuestionsByPosition(
            @PathVariable String positionName,
            @RequestParam(required = false, defaultValue = "asc") String difficultyOrder,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        return ResponseEntity.ok(questionService.getQuestionsByPosition(positionName, difficultyOrder, pageable));
    }
}

