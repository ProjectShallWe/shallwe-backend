package com.project.board.global.exception;

import com.project.board.global.response.ErrorCode;

public class CannotDeletePostException extends BaseException{

    public CannotDeletePostException() {
        super(ErrorCode.CANNOT_DELETE_POST);
    }

    public CannotDeletePostException(String message) {
        super(message, ErrorCode.CANNOT_DELETE_POST);
    }
}