package com.interview.techview.exception.post;

import com.interview.techview.exception.CustomException;
import com.interview.techview.exception.ErrorCode;

/**
 * 게시글을 찾을 수 없을 때 발생하는 예외
 */
public class PostNotFoundException extends CustomException {

    public PostNotFoundException() {
        super(ErrorCode.POST_NOT_FOUND);
    }
}

