package com.interview.techview.dto.common;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiResponse {
    private boolean success;
    private String message;

    public static ApiResponse ok(String message) {
        return ApiResponse.builder()
                .success(true)
                .message(message)
                .build();
    }
}
