package com.interview.techview.controller.post;

import com.interview.techview.domain.post.Post;
import com.interview.techview.dto.post.PostCreateRequest;
import com.interview.techview.dto.post.PostListResponse;
import com.interview.techview.dto.post.PostPasswordRequest;
import com.interview.techview.dto.post.PostResponse;
import com.interview.techview.dto.post.PostUpdateRequest;
import com.interview.techview.dto.common.PageResponse;
import com.interview.techview.security.CustomUserDetails;
import com.interview.techview.service.post.PostService;
import com.interview.techview.swagger.AuthApi;
import com.interview.techview.swagger.PublicApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Post", description = "게시글 관리 API")
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

        private final PostService postService;

        // 게시글 작성 API
        @Operation(summary = "게시글 작성", description = "새로운 게시글을 작성합니다. (인증 필요)")
        @AuthApi
        @PostMapping
        public ResponseEntity<PostResponse> addPost(
                        @Valid @RequestBody PostCreateRequest dto,
                        @AuthenticationPrincipal CustomUserDetails userDetails) {
                PostResponse post = postService.save(dto, userDetails.getId());
                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(post);
        }

        // 게시글 목록 조회 API (페이징)
        @Operation(summary = "게시글 목록 조회", description = "게시글 목록을 페이징하여 조회합니다. 기본 페이지 크기는 10입니다.")
        @PublicApi
        @GetMapping
        public ResponseEntity<PageResponse<PostListResponse>> getAllPosts(
                        @PageableDefault(size = 10, sort = "createAt") Pageable pageable) {
                PageResponse<Post> page = postService.findAll(pageable);
                List<PostListResponse> content = page.getContent()
                                .stream()
                                .map(PostListResponse::from)
                                .toList();
                PageResponse<PostListResponse> response = PageResponse.of(
                                content,
                                page.getTotalElements(),
                                page.getCurrentPage(),
                                page.getSize());
                return ResponseEntity.ok(response);
        }

        // 게시글 단건 조회 API
        @Operation(summary = "게시글 단건 조회", description = "게시글 ID로 단일 게시글의 상세 정보를 조회합니다.")
        @PublicApi
        @GetMapping("/{id}")
        public ResponseEntity<PostResponse> getPostById(@PathVariable Long id) {
                Post post = postService.findById(id);
                return ResponseEntity.ok(PostResponse.from(post));
        }

        // 게시글 삭제 API
        @Operation(summary = "게시글 삭제", description = "비밀번호를 확인하여 게시글을 삭제합니다.")
        @PublicApi
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deletePostById(
                        @PathVariable Long id,
                        @Valid @RequestBody PostPasswordRequest dto) {
                postService.delete(id, dto.getPassword());
                return ResponseEntity.ok().build();
        }

        // 게시글 수정 API
        @Operation(summary = "게시글 수정", description = "비밀번호를 확인하여 게시글을 수정합니다.")
        @PublicApi
        @PutMapping("/{id}")
        public ResponseEntity<PostResponse> updatePostById(
                        @PathVariable Long id,
                        @Valid @RequestBody PostUpdateRequest dto) {
                PostResponse post = postService.update(id, dto);
                return ResponseEntity.ok(post);
        }

        // 게시글 좋아요 추가/취소 API
        @Operation(summary = "게시글 좋아요", description = "게시글에 좋아요를 추가하거나 취소합니다. (인증 필요)")
        @AuthApi
        @PostMapping("/{id}/like")
        public ResponseEntity<PostResponse> toggleLike(
                        @PathVariable Long id,
                        @AuthenticationPrincipal CustomUserDetails userDetails) {
                PostResponse post = postService.toggleLike(id, userDetails.getId());
                return ResponseEntity.ok(post);
        }
}
