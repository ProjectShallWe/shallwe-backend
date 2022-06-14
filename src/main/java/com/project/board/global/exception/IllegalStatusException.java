package com.project.board.global.exception;

import com.project.board.global.response.ErrorCode;

public class IllegalStatusException extends BaseException{

    public IllegalStatusException() {
        super(ErrorCode.COMMON_ILLEGAL_STATUS);
    }

    public IllegalStatusException(String message) {
        super(message, ErrorCode.COMMON_ILLEGAL_STATUS);
    }
}
