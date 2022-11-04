package com.project.board.global.exception;

import com.project.board.global.response.ErrorCode;

public class CannotUpdatePostException extends BaseException{

    public CannotUpdatePostException() {
        super(ErrorCode.CANNOT_UPDATE_POST);
    }

    public CannotUpdatePostException(String message) {
        super(message, ErrorCode.CANNOT_UPDATE_POST);
    }
}