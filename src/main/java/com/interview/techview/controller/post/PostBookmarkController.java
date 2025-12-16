package com.interview.techview.controller.post;

import com.interview.techview.dto.post.PostListResponse;
import com.interview.techview.security.CustomUserDetails;
import com.interview.techview.service.post.PostBookmarkService;
import com.interview.techview.swagger.AuthApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "PostBookmark", description = "게시글 북마크 관리 API")
@RestController
@RequestMapping("/posts/bookmarks")
@RequiredArgsConstructor
public class PostBookmarkController {

    private final PostBookmarkService postBookmarkService;

    // 북마크 추가/삭제 API
    @Operation(summary = "북마크 추가/삭제", description = "게시글을 북마크에 추가하거나 삭제합니다. (인증 필요)")
    @AuthApi
    @PostMapping("/{postId}")
    public ResponseEntity<Void> toggleBookmark(
            @PathVariable Long postId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        postBookmarkService.toggleBookmark(postId, userDetails.getId());
        return ResponseEntity.ok().build();
    }

    // 북마크 목록 조회 API
    @Operation(summary = "북마크 목록 조회", description = "사용자가 북마크한 게시글 목록을 조회합니다. (인증 필요)")
    @AuthApi
    @GetMapping
    public ResponseEntity<List<PostListResponse>> getBookmarks(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        List<PostListResponse> bookmarks = postBookmarkService.getBookmarks(userDetails.getId());
        return ResponseEntity.ok(bookmarks);
    }

    // 북마크 여부 확인 API
    @Operation(summary = "북마크 여부 확인", description = "특정 게시글을 북마크했는지 확인합니다. (인증 필요)")
    @AuthApi
    @GetMapping("/{postId}/check")
    public ResponseEntity<Boolean> isBookmarked(
            @PathVariable Long postId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        boolean isBookmarked = postBookmarkService.isBookmarked(postId, userDetails.getId());
        return ResponseEntity.ok(isBookmarked);
    }
}

