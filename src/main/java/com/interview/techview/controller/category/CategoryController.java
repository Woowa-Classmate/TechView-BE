package com.interview.techview.controller.category;

import com.interview.techview.dto.category.CategoryCreateRequest;
import com.interview.techview.dto.category.CategoryResponse;
import com.interview.techview.dto.category.CategoryUpdateRequest;
import com.interview.techview.dto.common.ApiResponse;
import com.interview.techview.service.category.CategoryService;
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
        name = "Category",
        description = "면접 질문 분류를 위한 분야(Category) 관리 API"
)
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    // 분야 생성
    @Operation(
            summary = "분야 생성",
            description = "새로운 면접 질문 분야(Category)를 생성합니다."
    )
    @AuthApi
    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(
            @Valid @RequestBody CategoryCreateRequest request
    ) {
        return ResponseEntity.ok(categoryService.createCategory(request));
    }

    // 분야 조회
    @Operation(
            summary = "분야 목록 조회",
            description = "등록된 모든 면접 질문 분야 목록을 조회합니다."
    )
    @PublicApi
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    // 분야 수정
    @Operation(
            summary = "분야 수정",
            description = "기존 면접 질문 분야의 이름 또는 레벨을 수정합니다."
    )
    @AuthApi
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryUpdateRequest request
    ) {
        return ResponseEntity.ok(categoryService.updateCategory(id, request));
    }

    // 분야 삭제
    @Operation(
            summary = "분야 삭제",
            description = "면접 질문 분야(Category)를 삭제합니다."
    )
    @AuthApi
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(ApiResponse.ok("카테고리가 삭제되었습니다."));
    }
}

