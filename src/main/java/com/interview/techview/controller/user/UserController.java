package com.interview.techview.controller.user;

import com.interview.techview.dto.common.ApiResponse;
import com.interview.techview.dto.user.UpdateUserCategoriesRequest;
import com.interview.techview.dto.user.UpdateUserProfileRequest;
import com.interview.techview.dto.user.UpdateUserSkillsRequest;
import com.interview.techview.dto.user.UserProfileResponse;
import com.interview.techview.security.CustomUserDetails;
import com.interview.techview.service.user.UserService;
import com.interview.techview.swagger.AuthApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "User",
        description = "내 정보(마이페이지) 관리 API"
)
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 내 정보 조회
    @Operation(
            summary = "내 정보 조회",
            description = "로그인한 사용자의 프로필 정보를 조회합니다."
    )
    @AuthApi
    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getMyInfo(
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return ResponseEntity.ok(
                userService.getMyInfo(user.getId())
        );
    }

    // 내 정보 수정
    @Operation(
            summary = "내 정보 수정",
            description = "사용자 이름을 수정합니다."
    )
    @AuthApi
    @PutMapping("/me")
    public ResponseEntity<ApiResponse> updateMyInfo(
            @AuthenticationPrincipal CustomUserDetails user,
            @Valid @RequestBody UpdateUserProfileRequest request
    ) {
        userService.updateMyInfo(user.getId(), request);
        return ResponseEntity.ok(ApiResponse.ok("정보가 수정되었습니다."));
    }

    // 내 분야 설정
    @Operation(
            summary = "내 관심 분야 설정",
            description = "사용자의 관심 분야(Category)를 설정합니다."
    )
    @AuthApi
    @PutMapping("/me/categories")
    public ResponseEntity<ApiResponse> updateCategories(
            @AuthenticationPrincipal CustomUserDetails user,
            @Valid @RequestBody UpdateUserCategoriesRequest request
    ) {
        userService.updateCategories(user.getId(), request.getCategories());
        return ResponseEntity.ok(ApiResponse.ok("분야가 설정되었습니다."));
    }

    // 내 분야 조회
    @Operation(
            summary = "내 관심 분야 조회",
            description = "사용자가 설정한 관심 분야 목록을 조회합니다."
    )
    @AuthApi
    @GetMapping("/me/categories")
    public ResponseEntity<List<String>> getCategories(
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return ResponseEntity.ok(
                userService.getCategories(user.getId())
        );
    }

    // 내 기술스택 설정
    @Operation(
            summary = "내 기술스택 설정",
            description = "사용자의 기술스택을 설정합니다."
    )
    @AuthApi
    @PutMapping("/me/skills")
    public ResponseEntity<ApiResponse> updateSkills(
            @AuthenticationPrincipal CustomUserDetails user,
            @Valid @RequestBody UpdateUserSkillsRequest request
    ) {
        userService.updateSkills(user.getId(), request.getSkills());
        return ResponseEntity.ok(ApiResponse.ok("스킬이 설정되었습니다."));
    }

    // 내 기술스택 조회
    @Operation(
            summary = "내 기술스택 조회",
            description = "사용자가 설정한 기술스택 목록을 조회합니다."
    )
    @AuthApi
    @GetMapping("/me/skills")
    public ResponseEntity<List<String>> getSkills(
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return ResponseEntity.ok(
                userService.getSkills(user.getId())
        );
    }

}
