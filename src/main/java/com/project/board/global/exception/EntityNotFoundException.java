package com.project.board.global.exception;

import com.project.board.global.response.ErrorCode;

public class EntityNotFoundException extends BaseException{

    public EntityNotFoundException() {
        super(ErrorCode.COMMON_INVALID_PARAMETER);
    }

    public EntityNotFoundException(String message) {
        super(message, ErrorCode.COMMON_INVALID_PARAMETER);
    }
}
