package org.treeleaf.cache.ehcache;

import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.treeleaf.cache.Cache;
import org.treeleaf.cache.CacheException;
import org.treeleaf.common.json.Jsoner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 基于ehcache的缓存实现
 *
 * @author leaf
 * @date 2016-10-27 17:43
 */
public class EhCacheCacheImpl implements Cache {

    private static Logger log = LoggerFactory.getLogger(EhCacheCacheImpl.class);

    private static CacheManager manager = CacheManagerBuilder.newCacheManagerBuilder().build(true);
//            .with(CacheManagerBuilder.persistence("E://12306/data"))
//            .withCache("stations", CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, ArrayList.class, ResourcePoolsBuilder.heap(100000))
//                    .withExpiry(Expirations.timeToLiveExpiration(Duration.of(1, TimeUnit.HOURS))).build())
//
//            .withCache("tickets", CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, ArrayList.class, ResourcePoolsBuilder.heap(100000))
//                    .withExpiry(Expirations.timeToLiveExpiration(Duration.of(1, TimeUnit.MINUTES))).build())
//            .build(true);


    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("close CacheManager");
            manager.close();
        }));
    }

    private <K, V> org.ehcache.Cache<K, V> getCache(String key, Class<K> keyType, Class<V> valType) {
        org.ehcache.Cache cache = manager.getCache(key, keyType, valType);
        if (cache == null) {
            synchronized (EhCacheCacheImpl.class) {
                if (cache == null) {
                    CacheConfigurationBuilder cacheConfigurationBuilder = CacheConfigurationBuilder.newCacheConfigurationBuilder(keyType, valType, ResourcePoolsBuilder.heap(100000));
                    cache = manager.createCache(key, cacheConfigurationBuilder);
                }
            }
        }

        return cache;
    }


    @Override
    public void set(String key, Object value, int... expireSeconds) throws CacheException {
        String v;
        if (!(value instanceof String)) {
            v = Jsoner.toJson(value);
        } else {
            v = (String) value;
        }
        org.ehcache.Cache<String, String> cache = getCache(key, String.class, String.class);
        cache.put(key, v);
    }

    @Override
    public String get(String key) throws CacheException {
        org.ehcache.Cache<String, String> cache = getCache(key, String.class, String.class);
        return cache.get(key);
    }

    @Override
    public <T> T get(String key, Class<T> classz) throws CacheException {
        org.ehcache.Cache<String, String> cache = getCache(key, String.class, String.class);
        String v = cache.get(key);
        if (v != null) {
            return Jsoner.toObj(v, classz);
        }
        return null;
    }

    @Override
    public <T> List<T> mget(String[] keys, Class<T>... classz) throws CacheException {
        return null;
    }

    @Override
    public boolean del(String key) throws CacheException {
        return false;
    }

    @Override
    public boolean expire(String key, int seconds) throws CacheException {
        return false;
    }

    @Override
    public boolean exists(String key) throws CacheException {
        return false;
    }

    @Override
    public void setByNoExists(String key, Object value) throws CacheException {

    }

    @Override
    public void setMapValueByNoExists(String key, String entryKey, String value) throws CacheException {

    }

    @Override
    public void setMap(String key, Map<String, String> value, int... expireSeconds) throws CacheException {

    }

    @Override
    public Map<String, String> getMap(String key) throws CacheException {
        return null;
    }

    @Override
    public void setMapValue(String key, String entryKey, String entryValue) throws CacheException {

    }

    @Override
    public String getMapValue(String key, String entryKey) throws CacheException {
        return null;
    }

    @Override
    public void setStringList(String key, List<String> value, int... expireSeconds) throws CacheException {

    }

    @Override
    public <T> void setList(String key, List<T> value, int... expireSeconds) throws CacheException {

    }

    @Override
    public <T> List<T> getList(String key, Class<T>... classz) throws CacheException {
        return null;
    }

    @Override
    public void pushQueue(String key, Object value) throws CacheException {

    }

    @Override
    public <T> T popQueue(String key, Class<T>... classz) throws CacheException {
        return null;
    }

    @Override
    public long incrementBy(String key, int num) throws CacheException {
        return 0;
    }

    @Override
    public long incrementMapValueBy(String key, String entryKey, int num) {
        return 0;
    }
}
