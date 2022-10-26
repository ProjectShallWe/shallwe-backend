package com.project.board.global.exception;

import com.project.board.global.response.ErrorCode;

public class CustomAccessNotValidException extends BaseException{

    public CustomAccessNotValidException() {
        super(ErrorCode.JWT_ACCESS_NOT_VALID);
    }

    public CustomAccessNotValidException(String message) {
        super(message, ErrorCode.JWT_ACCESS_NOT_VALID);
    }
}
