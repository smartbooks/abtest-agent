package com.suiyueyule.abtest.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolUtils {
    
    private static JedisPool pool;

    private static void createJedisPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(100);
        config.setMaxIdle(10);
        pool = new JedisPool(config, "127.0.0.1", 6379);

    }

    private static synchronized void poolInit() {
        if (pool == null)
            createJedisPool();
    }

    public static Jedis getJedis() {
        if (pool == null)
            poolInit();
        return pool.getResource();
    }

    public static void returnRes(Jedis jedis) {
        pool.returnResource(jedis);
    }

}
