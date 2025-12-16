package com.interview.techview.exception;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 전역 예외 처리 클래스
 * 
 * 애플리케이션 전역에서 발생하는 예외를 한 곳에서 처리하여
 * 일관된 에러 응답을 제공합니다.
 * 
 * @RestControllerAdvice: 모든 컨트롤러에서 발생하는 예외를 처리
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * CustomException 처리
     * 
     * 비즈니스 로직에서 발생하는 커스텀 예외를 처리합니다.
     * ErrorCode에 정의된 상태 코드와 메시지를 응답합니다.
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ErrorResponse.of(errorCode));
    }

    /**
     * @Valid 검증 실패 처리
     * 
     * DTO에 @Valid 어노테이션이 적용된 파라미터의 유효성 검사가 실패했을 때
     * MethodArgumentNotValidException이 발생하며, 이를 처리합니다.
     * 
     * 모든 검증 실패 메시지를 수집하여 응답에 포함시킵니다.
     * 예: "제목은 필수입니다.", "비밀번호는 6자 이상 20자 이하로 작성해주세요."
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
        List<String> messages = e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        return ResponseEntity
                .badRequest()
                .body(ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, messages));
    }

    /**
     * 모든 예상치 못한 예외 처리
     * 
     * 위에서 처리하지 못한 모든 예외를 처리합니다.
     * 로그를 기록하고 500 Internal Server Error를 응답합니다.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("Unhandled exception occurred", e);
        return ResponseEntity
                .internalServerError()
                .body(ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR));
    }
}
