package com.interview.techview.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@Schema(description = "페이징 응답")
public class PageResponse<T> {

    @Schema(description = "데이터 리스트")
    private List<T> content;

    @Schema(description = "전체 요소 개수", example = "100")
    private long totalElements;

    @Schema(description = "전체 페이지 수", example = "10")
    private int totalPages;

    @Schema(description = "현재 페이지 (0부터 시작)", example = "0")
    private int currentPage;

    @Schema(description = "페이지 크기", example = "10")
    private int size;

    @Schema(description = "다음 페이지 존재 여부", example = "true")
    private boolean hasNext;

    @Schema(description = "이전 페이지 존재 여부", example = "false")
    private boolean hasPrevious;

    @Schema(description = "첫 번째 페이지 여부", example = "true")
    private boolean isFirst;

    @Schema(description = "마지막 페이지 여부", example = "false")
    private boolean isLast;

    public static <T> PageResponse<T> of(List<T> content, long totalElements, int currentPage, int size) {
        int totalPages = (int) Math.ceil((double) totalElements / size);
        
        return PageResponse.<T>builder()
                .content(content)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .currentPage(currentPage)
                .size(size)
                .hasNext(currentPage < totalPages - 1)
                .hasPrevious(currentPage > 0)
                .isFirst(currentPage == 0)
                .isLast(currentPage >= totalPages - 1)
                .build();
    }
}

