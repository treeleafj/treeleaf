package org.treeleaf.cache.redis;

import redis.clients.jedis.Jedis;

/**
 * jedis操作的封装,用于简化代码
 * <p/>
 * Created by yaoshuhong on 2015/6/4.
 */
public interface JedisHandler {

    Object handler(Jedis jedis);

}
