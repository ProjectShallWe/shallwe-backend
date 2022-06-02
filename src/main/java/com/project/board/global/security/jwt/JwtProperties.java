package com.project.board.global.security.jwt;

public interface JwtProperties {
    String SECRET = "board";
    int EXPIRATION_TIME = 1800000;
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
