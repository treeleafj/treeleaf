package org.treeleaf.cache.redis;

import org.treeleaf.cache.CacheConfig;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * jedisPool的工厂,用于屏蔽掉具体的初始化工作
 * <p/>
 * Created by yaoshuhong on 2015/6/3.
 */
public class JedisPoolFactory {

    /**
     * 默认数据库是0
     */
    public static final int DEFAULT_DATABASE = 0;

    /**
     * 默认超时时间
     */
    public static final int DEFAULT_TIMEOUT = 10000;

    private static volatile JedisPool jedisPool = null;

    public static JedisPool getPool() {
        if (jedisPool == null) {
            //加锁防止重复构建
            synchronized (JedisPoolFactory.class) {

                if (jedisPool != null) {
                    return jedisPool;
                }

                JedisPoolConfig poolConfig = new JedisPoolConfig();
                CacheConfig cacheConfig = CacheConfig.getInstance();
                poolConfig.setMaxIdle(cacheConfig.getMaxIdle());
                poolConfig.setMaxTotal(cacheConfig.getMaxTotal());
                poolConfig.setMaxWaitMillis(cacheConfig.getMaxWaitmillis());
//                poolConfig.setTestOnBorrow(true);//保证每次的jedis对象都是可用的,暂不启用,会导致性能下降一半

                jedisPool = new JedisPool(poolConfig, cacheConfig.getHost(), cacheConfig.getPort(), DEFAULT_TIMEOUT, cacheConfig.getPassword(), DEFAULT_DATABASE, null);
            }
        }
        return jedisPool;
    }

}
