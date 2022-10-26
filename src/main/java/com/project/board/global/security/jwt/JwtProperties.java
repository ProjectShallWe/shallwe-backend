package com.project.board.global.security.jwt;

public interface JwtProperties {
    String SECRET = "community";
    String ISSUER = "shallwe";
    String EMAIL = "email";
    String NICKNAME = "nickname";
    Long ACCESS_EXPIRATION_TIME = 1000*60*30L; // 30분
    Long REFRESH_EXPIRATION_TIME = 1000*60*60*24*14L; // 2주
    String TOKEN_PREFIX = "Bearer ";
    String ACCESS_HEADER_PREFIX = "Authorization";
    String ACCESS_TOKEN = "access";
    String REFRESH_TOKEN = "refresh";
}
