package com.project.board.global.exception;

import com.project.board.global.response.ErrorCode;

public class CustomRefreshNotValidException extends BaseException{

    public CustomRefreshNotValidException() {
        super(ErrorCode.JWT_REFRESH_NOT_VALID);
    }

    public CustomRefreshNotValidException(String message) {
        super(message, ErrorCode.JWT_REFRESH_NOT_VALID);
    }
}
