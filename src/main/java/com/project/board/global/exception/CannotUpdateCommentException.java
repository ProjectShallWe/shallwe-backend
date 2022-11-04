package com.project.board.global.exception;

import com.project.board.global.response.ErrorCode;

public class CannotUpdateCommentException extends BaseException{

    public CannotUpdateCommentException() {
        super(ErrorCode.CANNOT_UPDATE_COMMENT);
    }

    public CannotUpdateCommentException(String message) {
        super(message, ErrorCode.CANNOT_UPDATE_COMMENT);
    }
}