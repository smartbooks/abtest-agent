package com.suiyueyule.abtest.util;

import com.suiyueyule.abtest.cache.RedisConfig;
import com.suiyueyule.abtest.config.ConfigManage;
import redis.clients.jedis.Jedis;

/**
 * Redis帮助类
 */
public class JedisUtils {

    /**
     * 根据系统当前配置初始化一个Redis对象实例
     *
     * @return
     */
    public static Jedis getJedis() {

        RedisConfig conf = ConfigManage.Instance.recommendRedisConfig;

        return getJedis(conf);
    }

    /**
     * 根据配置对象初始化Redis对象实例
     *
     * @param conf Redis配置
     * @return
     */
    public static Jedis getJedis(RedisConfig conf) {

        Jedis jedis = new Jedis(conf.host, conf.port, conf.connectionTimeout, conf.useSSL);
        jedis.select(conf.dbIndex);

        if (conf.auth != null && conf.auth.isEmpty() == false) {
            jedis.auth(conf.auth);
        }

        return jedis;
    }

}
