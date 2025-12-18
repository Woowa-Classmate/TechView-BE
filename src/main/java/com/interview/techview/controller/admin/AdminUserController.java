package com.interview.techview.controller.admin;

import com.interview.techview.dto.common.ApiResponse;
import com.interview.techview.dto.user.AdminUserCreateRequest;
import com.interview.techview.dto.user.AdminUserUpdateRequest;
import com.interview.techview.dto.user.UserListResponse;
import com.interview.techview.service.user.UserService;
import com.interview.techview.swagger.AuthApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Admin", description = "관리자 API")
@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserService userService;

    // 모든 사용자 조회 (관리자용)
    @Operation(summary = "모든 사용자 조회", description = "모든 사용자의 이름, 이메일, 권한 정보를 조회합니다. (관리자 권한 필요)")
    @AuthApi
    @GetMapping
    public ResponseEntity<List<UserListResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // 사용자 검색 (관리자용)
    @Operation(summary = "사용자 검색", description = "이메일 또는 이름으로 사용자를 검색합니다. 키워드가 없으면 모든 사용자를 조회합니다. (관리자 권한 필요)")
    @AuthApi
    @GetMapping("/search")
    public ResponseEntity<List<UserListResponse>> searchUsers(
            @RequestParam(required = false) String keyword) {
        return ResponseEntity.ok(userService.searchUsers(keyword));
    }

    // 사용자 추가 (관리자용)
    @Operation(summary = "사용자 추가", description = "새로운 사용자를 생성합니다. (관리자 권한 필요)")
    @AuthApi
    @PostMapping
    public ResponseEntity<UserListResponse> createUser(
            @Valid @RequestBody AdminUserCreateRequest request) {
        UserListResponse createdUser = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    // 사용자 수정 (관리자용)
    @Operation(summary = "사용자 수정", description = "사용자의 이메일, 이름, 권한을 수정합니다. (관리자 권한 필요)")
    @AuthApi
    @PutMapping("/{userId}")
    public ResponseEntity<UserListResponse> updateUser(
            @PathVariable Long userId,
            @Valid @RequestBody AdminUserUpdateRequest request) {
        UserListResponse updatedUser = userService.updateUser(userId, request);
        return ResponseEntity.ok(updatedUser);
    }

    // 사용자 삭제 (관리자용)
    @Operation(summary = "사용자 삭제", description = "사용자를 삭제합니다. (관리자 권한 필요)")
    @AuthApi
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok(ApiResponse.ok("사용자가 삭제되었습니다."));
    }
}
