package com.project.board.global.redis;

public class CacheKey {
    private CacheKey() {

    }
    public static final int DEFAULT_EXPIRE_SEC = 60;

    public static final String USER = "user";
    public static final int USER_EXPIRE_SEC = 60;

    public static final String POST = "post";
    public static final int POST_EXPIRE_SEC = 60;

    public static final String RECOMMEND_POST = "recommendPost";
    public static final int RECOMMEND_POST_EXPIRE_SEC = 60;
}
