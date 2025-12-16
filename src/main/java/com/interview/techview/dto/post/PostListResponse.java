package com.interview.techview.dto.post;

import com.interview.techview.domain.post.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Schema(description = "게시글 목록 응답 DTO")
@Getter
@Builder
public class PostListResponse {

    @Schema(description = "게시글 ID", example = "1")
    private Long postId;

    @Schema(description = "게시글 제목")
    private String title;

    @Schema(description = "작성자 이름")
    private String name;

    @Schema(description = "좋아요 개수", example = "0")
    private int likeCount;

    @Schema(description = "조회수", example = "0")
    private int viewCount;

    @Schema(description = "생성일시")
    private LocalDateTime createAt;

    public static PostListResponse from(Post post) {
        return PostListResponse.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .name(post.getUser().getName())
                .likeCount(post.getLikeCount())
                .viewCount(post.getViewCount())
                .createAt(post.getCreateAt())
                .build();
    }
}
