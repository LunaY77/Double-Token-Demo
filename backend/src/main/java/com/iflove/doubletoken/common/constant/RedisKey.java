package com.iflove.doubletoken.common.constant;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */
public class RedisKey {
    private static final String BASE_KEY = "DoubleToken:";

    // RefreshToken
    public static final String REFRESH_TOKEN = "refresh_token:%s";

    public static String getKey(String key, Object... objects) {
        return BASE_KEY + String.format(key, objects);
    }
}
