package com.interview.techview.controller.user;

import com.interview.techview.dto.common.ApiResponse;
import com.interview.techview.dto.user.UpdateUserCategoriesRequest;
import com.interview.techview.dto.user.UpdateUserProfileRequest;
import com.interview.techview.dto.user.UpdateUserSkillsRequest;
import com.interview.techview.dto.user.UserProfileResponse;
import com.interview.techview.security.CustomUserDetails;
import com.interview.techview.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 내 정보 조회
    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getMyInfo(
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return ResponseEntity.ok(
                userService.getMyInfo(user.getId())
        );
    }

    // 내 정보 수정
    @PutMapping("/me")
    public ResponseEntity<ApiResponse> updateMyInfo(
            @AuthenticationPrincipal CustomUserDetails user,
            @Valid @RequestBody UpdateUserProfileRequest request
    ) {
        userService.updateMyInfo(user.getId(), request);
        return ResponseEntity.ok(ApiResponse.ok("정보가 수정되었습니다."));
    }

    // 내 분야 설정
    @PutMapping("/me/categories")
    public ResponseEntity<ApiResponse> updateCategories(
            @AuthenticationPrincipal CustomUserDetails user,
            @Valid @RequestBody UpdateUserCategoriesRequest request
    ) {
        userService.updateCategories(user.getId(), request.getCategories());
        return ResponseEntity.ok(ApiResponse.ok("분야가 설정되었습니다."));
    }

    // 내 분야 조회
    @GetMapping("/me/categories")
    public ResponseEntity<?> getCategories(
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return ResponseEntity.ok(
                userService.getCategories(user.getId())
        );
    }

    // 내 기술스택 설정
    @PutMapping("/me/skills")
    public ResponseEntity<ApiResponse> updateSkills(
            @AuthenticationPrincipal CustomUserDetails user,
            @Valid @RequestBody UpdateUserSkillsRequest request
    ) {
        userService.updateSkills(user.getId(), request.getSkills());
        return ResponseEntity.ok(ApiResponse.ok("스킬이 설정되었습니다."));
    }

    // 내 기술스택 조회
    @GetMapping("/me/skills")
    public ResponseEntity<?> getSkills(
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return ResponseEntity.ok(
                userService.getSkills(user.getId())
        );
    }
}
