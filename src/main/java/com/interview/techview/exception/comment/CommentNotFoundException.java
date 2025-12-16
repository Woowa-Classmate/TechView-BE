package com.interview.techview.exception.comment;

import com.interview.techview.exception.CustomException;
import com.interview.techview.exception.ErrorCode;

public class CommentNotFoundException extends CustomException {
    public CommentNotFoundException() {
        super(ErrorCode.COMMENT_NOT_FOUND);
    }
}

