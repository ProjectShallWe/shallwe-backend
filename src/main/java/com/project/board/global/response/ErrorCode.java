package com.project.board.global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    COMMON_SYSTEM_ERROR("일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요."), // 장애 상황
    COMMON_INVALID_PARAMETER("요청한 값이 올바르지 않습니다."),
    COMMON_ENTITY_NOT_FOUND("존재하지 않는 엔티티입니다."),
    COMMON_ILLEGAL_STATUS("잘못된 상태값입니다."),
    JWT_ACCESS_NOT_VALID("유효하지 않은 JWT 토큰입니다."),
    JWT_REFRESH_NOT_VALID("유효하지 않은 JWT 토큰입니다."),
    JWT_ACCESS_TOKEN_EXPIRED("만료된 access 토큰입니다."),
    JWT_REFRESH_TOKEN_EXPIRED("만료된 refresh 토큰입니다."),
    JWT_SIGNATURE_VERIFICATION("유효하지 않은 JWT 토큰입니다.");

    private final String errorMsg;

    public String getErrorMsg(Object... arg) {
        return String.format(errorMsg, arg);
    }
}
