package com.yuanqi.util;

import redis.clients.jedis.Jedis;

import java.util.Collections;

public class RedisLockUtil {
    private static Jedis jedis = new Jedis("127.0.0.1", 6379);

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NO_EXITS = "NX";         //只有key不存在才设值
    private static final String SET_WITH_EXPIRE_TIME = "PX";    //过期单位时间单位：毫秒

    private static final Long RELEASE_SUCCESS = 1L;

    /**加锁**/
    public static boolean tryGetDistributedLock(String lockKey, String requestId, int expireTime) {
        //set(String key, String value, String nxxx, String expx, int time)
        //第三个参数nxxx: 1、nx(not exists),只有key不存在时,才把key value set 到redis
        //              2、xx(is exists),只有key存在时,才把key value set 到redis
        //第四个参数expx：有两个值可选，"ex" 代表 seconds 秒，"px" 代表 milliseconds 毫秒
        String result = jedis.set(lockKey, requestId, SET_IF_NO_EXITS, SET_WITH_EXPIRE_TIME, expireTime);
        
        if (LOCK_SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }

    /**解锁**/
    public static boolean releaseDistributedLock(String lockKey, String requestId) {
        /*
            LUA脚本。根据key得到requestId，判断这个id和客户端的id是否相同，相同就可以执行删除操作。否则失败返回 0
         */
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

        //Redis Eval 命令使用 Lua 解释器执行脚本。Collections.singletonList 把唯一的对象变成不可变的集合List
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
        if (RELEASE_SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
//        boolean resultLock = tryGetDistributedLock("test1", "request1", 500000);
//        System.out.println(resultLock);

        boolean resultUnlock = releaseDistributedLock("test1", "request1");
        System.out.println(resultUnlock);
    }

}
