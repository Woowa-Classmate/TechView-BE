package com.interview.techview.controller.admin;

import com.interview.techview.domain.post.Post;
import com.interview.techview.dto.common.ApiResponse;
import com.interview.techview.dto.post.AdminNoticeCreateRequest;
import com.interview.techview.dto.post.AdminPostUpdateRequest;
import com.interview.techview.dto.post.PostResponse;
import com.interview.techview.security.CustomUserDetails;
import com.interview.techview.service.post.PostService;
import com.interview.techview.swagger.AuthApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Admin", description = "관리자 API")
@RestController
@RequestMapping("/admin/posts")
@RequiredArgsConstructor
public class AdminPostController {

    private final PostService postService;

    // 모든 게시글 조회 (관리자용)
    @Operation(summary = "모든 게시글 조회", description = "모든 게시글을 조회합니다. (관리자 권한 필요)")
    @AuthApi
    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        List<PostResponse> posts = postService.findAll()
                .stream()
                .map(PostResponse::from)
                .toList();
        return ResponseEntity.ok(posts);
    }

    // 게시글 검색 (관리자용)
    @Operation(
        summary = "게시글 검색",
        description = "제목으로 게시글을 검색합니다. 키워드가 없으면 모든 게시글을 조회합니다. (관리자 권한 필요)"
    )
    @AuthApi
    @GetMapping("/search")
    public ResponseEntity<List<PostResponse>> searchPosts(
            @RequestParam(required = false) String keyword) {
        List<PostResponse> posts = postService.searchByTitle(keyword)
                .stream()
                .map(PostResponse::from)
                .toList();
        return ResponseEntity.ok(posts);
    }

    // 게시글 상세 조회 (관리자용 - 조회수 증가 안 함)
    @Operation(summary = "게시글 상세 조회", description = "게시글 ID로 단일 게시글의 상세 정보를 조회합니다. 조회수가 증가하지 않습니다. (관리자 권한 필요)")
    @AuthApi
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostDetail(@PathVariable Long id) {
        Post post = postService.findByIdForAdmin(id);
        return ResponseEntity.ok(PostResponse.from(post));
    }

    // 공지사항 추가 (관리자용)
    @Operation(summary = "공지사항 추가", description = "새로운 공지사항을 작성합니다. 공지사항으로 자동 설정되며 비밀번호는 자동 생성됩니다. (관리자 권한 필요)")
    @AuthApi
    @PostMapping
    public ResponseEntity<PostResponse> createNotice(
            @Valid @RequestBody AdminNoticeCreateRequest request,
            @AuthenticationPrincipal CustomUserDetails admin) {
        PostResponse notice = postService.createNotice(
                request.getTitle(),
                request.getContent(),
                admin.getId()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(notice);
    }

    // 게시글 수정 (관리자용)
    @Operation(summary = "게시글 수정", description = "게시글 제목과 내용을 수정합니다. 비밀번호 검증 없이 수정 가능합니다. (관리자 권한 필요)")
    @AuthApi
    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody AdminPostUpdateRequest request) {
        PostResponse updatedPost = postService.updateForAdmin(id, request.getTitle(), request.getContent());
        return ResponseEntity.ok(updatedPost);
    }

    // 게시글 삭제 (관리자용)
    @Operation(summary = "게시글 삭제", description = "게시글을 삭제합니다. 비밀번호 검증 없이 삭제 가능합니다. (관리자 권한 필요)")
    @AuthApi
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Long id) {
        postService.deleteForAdmin(id);
        return ResponseEntity.ok(ApiResponse.ok("게시글이 삭제되었습니다."));
    }
}

