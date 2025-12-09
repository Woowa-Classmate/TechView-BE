package com.interview.techview.exception;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ErrorResponse {

    private String code;
    private String message;
    private List<String> errors;

    public static ErrorResponse of(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .build();
    }

    public static ErrorResponse of(ErrorCode errorCode, List<String> errors) {
        return ErrorResponse.builder()
                .code(errorCode.name())
                .message(errorCode.getMessage())  // 대표 메시지
                .errors(errors)  // 실제 필드 검증 등 상세 오류
                .build();
    }
}
