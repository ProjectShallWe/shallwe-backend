package com.project.board.global.exception;

import com.project.board.global.response.ErrorCode;

public class CannotDeleteCommentException extends BaseException{

    public CannotDeleteCommentException() {
        super(ErrorCode.CANNOT_DELETE_COMMENT);
    }

    public CannotDeleteCommentException(String message) {
        super(message, ErrorCode.CANNOT_DELETE_COMMENT);
    }
}