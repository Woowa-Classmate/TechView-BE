package com.interview.techview.exception;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ErrorResponse {

    private String code;
    private Object message;

    public static ErrorResponse of(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .build();
    }

    public static ErrorResponse of(ErrorCode errorCode, List<String> messages) {
        return ErrorResponse.builder()
                .code(errorCode.name())
                .message(messages)
                .build();
    }
}
