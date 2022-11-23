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

    public static final String REAL_TIME_BEST_POST = "realTimeBestPost";
    public static final int REAL_TIME_BEST_POST_EXPIRE_SEC = 60;

    public static final String RECOMMEND_BOARDS= "recommendBoards";
    public static final int RECOMMEND_BOARDS_EXPIRE_SEC = 60*60;
    public static final String REFRESH_TOKEN = "refreshToken::";
    public static final int REFRESH_TOKEN_EXPIRE_SEC = 60*60*24*14;

}
