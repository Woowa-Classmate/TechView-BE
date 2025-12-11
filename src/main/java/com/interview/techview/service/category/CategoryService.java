package com.interview.techview.service.category;

import com.interview.techview.domain.category.Category;
import com.interview.techview.dto.category.CategoryCreateRequest;
import com.interview.techview.dto.category.CategoryResponse;
import com.interview.techview.dto.category.CategoryUpdateRequest;
import com.interview.techview.exception.CustomException;
import com.interview.techview.exception.ErrorCode;
import com.interview.techview.repository.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // 분야 생성
    public CategoryResponse createCategory(CategoryCreateRequest request) {

        if (categoryRepository.existsByName(request.getName())) {
            throw new CustomException(ErrorCode.DUPLICATE_CATEGORY);
        }

        Category category = categoryRepository.save(
                Category.builder()
                        .name(request.getName())
                        .level(request.getLevel())
                        .build()
        );

        return CategoryResponse.from(category);
    }

    // 분야 조회
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(CategoryResponse::from)
                .toList();
    }

    // 분야 수정
    public CategoryResponse updateCategory(Long id, CategoryUpdateRequest request) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));

        // name 수정 시만 중복 체크
        if (request.getName() != null) {
            if (categoryRepository.existsByName(request.getName())) {
                throw new CustomException(ErrorCode.DUPLICATE_CATEGORY);
            }
            category.updateName(request.getName());
        }

        // level 수정 시만 변경
        if (request.getLevel() != null) {
            category.updateLevel(request.getLevel());
        }

        categoryRepository.save(category);

        return CategoryResponse.from(category);
    }
    // 분야 삭제
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new CustomException(ErrorCode.CATEGORY_NOT_FOUND);
        }
        categoryRepository.deleteById(id);
    }
}

