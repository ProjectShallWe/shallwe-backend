package com.project.board.global.exception;

import com.project.board.global.response.ErrorCode;

public class CustomRefreshTokenExpiredException extends BaseException{

    public CustomRefreshTokenExpiredException() {
        super(ErrorCode.JWT_REFRESH_TOKEN_EXPIRED);
    }

    public CustomRefreshTokenExpiredException(String message) {
        super(message, ErrorCode.JWT_REFRESH_TOKEN_EXPIRED);
    }
}
