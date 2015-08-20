package org.treeleaf.cache.local;

import org.treeleaf.cache.Cache;
import org.treeleaf.cache.CacheException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yaoshuhong on 2015/8/20.
 */
public class LocalCacheImpl implements Cache {

    private static Map<String, Object> cache = new HashMap<String, Object>();
    private static Map<String, Map> cacheMap = new HashMap<String, Map>();
    private static Map<String, List> cacheList = new HashMap<String, List>();

    @Override
    public void set(String key, Object object, int... expireSeconds) throws CacheException {
        cache.put(key, object);
    }

    @Override
    public <T> T get(String key, Class<T> classz) throws CacheException {
        return (T) cache.get(key);
    }

    @Override
    public boolean del(String key) throws CacheException {
        Object r = cache.remove(key);
        return r != null;
    }

    @Override
    public boolean expire(String key, int seconds) throws CacheException {
        return false;
    }

    @Override
    public boolean exists(String key) throws CacheException {
        return cache.containsKey(key);
    }

    @Override
    public void setByNoExists(String key, Object value) throws CacheException {
        if (!cache.containsKey(key)) {
            cache.put(key, value);
        }
    }

    @Override
    public void setMapValueByNoExists(String key, String entryKey, String value) throws CacheException {
        Map m = cacheMap.get(key);
        if (m != null) {
            m.put(entryKey, value);
        }
    }

    @Override
    public String get(String key) throws CacheException {
        return (String) cache.get(key);
    }

    @Override
    public void setMap(String key, Map<String, String> value, int... expireSeconds) throws CacheException {
        cacheMap.put(key, value);
    }

    @Override
    public Map<String, String> getMap(String key) throws CacheException {
        return cacheMap.get(key);
    }

    @Override
    public void setMapValue(String key, String entryKey, String entryValue) throws CacheException {
        Map m = cacheMap.get(key);
        if (m == null) {
            m = new HashMap();
            cacheMap.put(key, m);
        }
        m.put(entryKey, entryValue);
    }

    @Override
    public String getMapValue(String key, String entryKey) throws CacheException {
        Map m = cacheMap.get(key);
        if (m != null) {
            return (String) m.get(entryKey);
        }
        return null;
    }

    @Override
    public void setStringList(String key, List<String> value, int... expireSeconds) throws CacheException {
        cacheList.put(key, value);
    }

    @Override
    public <T> void setList(String key, List<T> value, int... expireSeconds) throws CacheException {
        cacheList.put(key, value);
    }

    @Override
    public <T> List<T> getList(String key, Class<T>... classz) throws CacheException {
        return cacheList.get(key);
    }

    @Override
    public void pushQueue(String key, Object value) throws CacheException {
        List list = cacheList.get(key);
        if (list == null) {
            list = new ArrayList();
            cacheList.put(key, list);
        }
        list.add(value);
    }

    @Override
    public <T> T popQueue(String key, Class<T>... classz) throws CacheException {
        List list = cacheList.get(key);
        if (list != null && list.size() > 0) {
            return (T) list.get(0);
        }
        return null;
    }

    @Override
    public long incrementBy(String key, int num) throws CacheException {
        throw new RuntimeException("暂未实现");
    }

    @Override
    public long incrementMapValueBy(String key, String entryKey, int num) {
        throw new RuntimeException("暂未实现");
    }
}
