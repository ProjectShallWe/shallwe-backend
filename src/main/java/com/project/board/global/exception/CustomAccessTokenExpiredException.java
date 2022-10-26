package com.project.board.global.exception;

import com.project.board.global.response.ErrorCode;

public class CustomAccessTokenExpiredException extends BaseException{

    public CustomAccessTokenExpiredException() {
        super(ErrorCode.JWT_ACCESS_TOKEN_EXPIRED);
    }

    public CustomAccessTokenExpiredException(String message) {
        super(message, ErrorCode.JWT_ACCESS_TOKEN_EXPIRED);
    }
}
