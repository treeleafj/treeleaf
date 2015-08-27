package org.treeleaf.cache.redis;

import org.junit.BeforeClass;
import org.junit.Test;
import org.treeleaf.cache.Cache;
import org.treeleaf.cache.CacheConfig;
import org.treeleaf.cache.CacheFactory;
import org.treeleaf.common.json.Jsoner;

public class RedisCacheImplTest {

    private Cache cache = CacheFactory.getCache();

    @BeforeClass
    public static void beforeClass() {
        CacheConfig cacheConfig = new CacheConfig();
        cacheConfig.setHost("172.30.0.102");
        cacheConfig.setPassword("C1487A992AA022A3B30C9ADD8B1DD1DD");
        cacheConfig.setPort(6379);
        cacheConfig.setMaxTotal(30);
        cacheConfig.setMaxIdle(10);
        cacheConfig.setMaxWaitmillis(10000);
        cacheConfig.init();
    }

    @Test
    public void testPushQueueValue() throws Exception {
        User user1 = new User();
        user1.setUsername("张三1");
        user1.setPassword("123456");

        User user2 = new User();
        user2.setUsername("张三2");
        user2.setPassword("123456");

        User user3 = new User();
        user3.setUsername("张三3");
        user3.setPassword("123456");

        cache.pushQueue("q1", user1);
        cache.pushQueue("q1", user2);
        cache.pushQueue("q1", user3);
    }

    @Test
    public void testPopQueueValue() throws Exception {
        System.out.println(Jsoner.toJson(cache.popQueue("q1")));
        System.out.println(Jsoner.toJson(cache.popQueue("q1")));
        System.out.println(Jsoner.toJson(cache.popQueue("q1", User.class)));
        System.out.println(Jsoner.toJson(cache.popQueue("q1", User.class)));
    }


}