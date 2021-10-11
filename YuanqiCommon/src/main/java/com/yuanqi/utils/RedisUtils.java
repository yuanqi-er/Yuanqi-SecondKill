package com.yuanqi.utils;

import redis.clients.jedis.Jedis;

public class RedisUtils {
    private static Jedis jedis = new Jedis("127.0.0.1", 6379);

    /**
     * Redis 根据 key 获得 value
     * @param key
     * @return
     */
    public static String get(String key) {
        String result = jedis.get(key);
        return result;
    }

    /**
     * Redis 设置 key 和 value
     * @param key
     * @param value
     */
    public static void set(String key, String value) {
        String result = jedis.set(key, value);
    }

}
