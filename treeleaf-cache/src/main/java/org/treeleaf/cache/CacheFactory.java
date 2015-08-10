package org.treeleaf.cache;

import org.treeleaf.cache.redis.RedisCacheImpl;

/**
 * Cache对象构建工厂,屏蔽Cache接口的具体实现和构建等工作
 * <p/>
 * Created by yaoshuhong on 2015/6/3.
 */
public class CacheFactory {

    private static Cache cache = new RedisCacheImpl();

    /**
     * 获取Cache对象
     *
     * @return
     */
    public static Cache getCache() {
        return cache;
    }

}
