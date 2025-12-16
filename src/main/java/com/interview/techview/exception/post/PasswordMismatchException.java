package com.interview.techview.exception.post;

import com.interview.techview.exception.CustomException;
import com.interview.techview.exception.ErrorCode;

/**
 * 게시글 비밀번호가 일치하지 않을 때 발생하는 예외
 */
public class PasswordMismatchException extends CustomException {

    public PasswordMismatchException() {
        super(ErrorCode.PASSWORD_MISMATCH);
    }
}

