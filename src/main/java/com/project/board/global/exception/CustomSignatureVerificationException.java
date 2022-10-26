package com.project.board.global.exception;

import com.project.board.global.response.ErrorCode;

public class CustomSignatureVerificationException extends BaseException{

    public CustomSignatureVerificationException() {
        super(ErrorCode.JWT_SIGNATURE_VERIFICATION);
    }

    public CustomSignatureVerificationException(String message) {
        super(message, ErrorCode.JWT_SIGNATURE_VERIFICATION);
    }
}
