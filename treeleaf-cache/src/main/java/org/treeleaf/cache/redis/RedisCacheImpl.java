package org.treeleaf.cache.redis;

import org.apache.commons.lang3.StringUtils;
import org.treeleaf.cache.Cache;
import org.treeleaf.cache.CacheException;
import org.treeleaf.common.json.JsonUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 基于Redis的缓存实现
 * <p/>
 * Created by yaoshuhong on 2015/6/3.
 */
public class RedisCacheImpl implements Cache {

    @Override
    public void setObj(String key, Object object) throws CacheException {
        String json = JsonUtils.toJson(object);
        this.set(key, json);
    }

    @Override
    public void setObj(String key, Object object, int expireSeconds) throws CacheException {
        String json = JsonUtils.toJson(object);
        this.set(key, json, expireSeconds);
    }

    @Override
    public <T extends Object> T getObj(String key, Class<T> classz) throws CacheException {
        String json = this.get(key);
        if (StringUtils.isBlank(json)) {
            return null;
        }
        return JsonUtils.parseObject(json, classz);
    }

    @Override
    public boolean del(String key) throws CacheException {
        final String _key = key;
        return this.handler(new JedisHandler() {
            @Override
            public Object handler(Jedis jedis) {
                long r = jedis.del(_key);
                return r > 0;
            }
        });
    }

    @Override
    public boolean expire(String key, int seconds) throws CacheException {
        final String _key = key;
        final int _seconds = seconds;
        return this.handler(new JedisHandler() {
            @Override
            public Object handler(Jedis jedis) {
                long r = jedis.expire(_key, _seconds);
                return r > 0;
            }
        });

    }

    @Override
    public boolean exists(String key) throws CacheException {
        final String _key = key;
        return this.handler(new JedisHandler() {
            @Override
            public Object handler(Jedis jedis) {
                return jedis.exists(_key);
            }
        });
    }

    @Override
    public void set(String key, String value) throws CacheException {

        final String _key = key;
        final String _value = value;
        this.handler(new JedisHandler() {
            @Override
            public Object handler(Jedis jedis) {
                return jedis.set(_key, _value);
            }
        });

    }

    @Override
    public void setByNoExists(String key, String value) throws CacheException {
        final String _key = key;
        final String _value = value;
        this.handler(new JedisHandler() {
            @Override
            public Object handler(Jedis jedis) {
                return jedis.setnx(_key, _value);
            }
        });
    }

    @Override
    public void setMapValueByNoExists(String key, String entryKey, String value) throws CacheException {
        final String _key = key;
        final String _entryKey = entryKey;
        final String _value = value;
        this.handler(new JedisHandler() {
            @Override
            public Object handler(Jedis jedis) {
                return jedis.hsetnx(_key, _entryKey, _value);
            }
        });
    }

    @Override
    public void set(String key, String value, int expireSeconds) throws CacheException {
        final String _key = key;
        final String _value = value;
        final int _expireSeconds = expireSeconds;
        this.handler(new JedisHandler() {
            @Override
            public Object handler(Jedis jedis) {
                return jedis.setex(_key, _expireSeconds, _value);
            }
        });
    }

    @Override
    public String get(String key) throws CacheException {
        final String _key = key;
        return this.handler(new JedisHandler() {
            @Override
            public Object handler(Jedis jedis) {
                return jedis.get(_key);
            }
        });
    }

    @Override
    public void setMap(String key, Map<String, String> value) throws CacheException {
        final String _key = key;
        final Map<String, String> _value = value;
        this.handler(new JedisHandler() {
            @Override
            public Object handler(Jedis jedis) {
                return jedis.hmset(_key, _value);
            }
        });
    }

    @Override
    public void setMap(String key, Map<String, String> value, int expireSeconds) throws CacheException {
        final String _key = key;
        final Map<String, String> _value = value;
        final int _expireSeconds = expireSeconds;
        this.handler(new JedisHandler() {
            @Override
            public Object handler(Jedis jedis) {
                jedis.hmset(_key, _value);
                return jedis.expire(_key, _expireSeconds);
            }
        });
    }

    @Override
    public Map<String, String> getMap(String key) throws CacheException {
        final String _key = key;
        return this.handler(new JedisHandler() {
            @Override
            public Object handler(Jedis jedis) {
                return jedis.hgetAll(_key);
            }
        });
    }

    @Override
    public void setMapValue(String key, String entryKey, String entryValue) throws CacheException {
        final String _key = key;
        final String _entryKey = entryKey;
        final String _entryValue = entryValue;
        this.handler(new JedisHandler() {
            @Override
            public Object handler(Jedis jedis) {
                return jedis.hset(_key, _entryKey, _entryValue);
            }
        });
    }

    @Override
    public String getMapValue(String key, String entryKey) throws CacheException {
        final String _key = key;
        final String _entryKey = entryKey;
        return this.handler(new JedisHandler() {
            @Override
            public Object handler(Jedis jedis) {
                return jedis.hget(_key, _entryKey);
            }
        });
    }

    @Override
    public void setStringList(String key, List<String> value) throws CacheException {
        final String _key = key;
        final String[] _values = value.toArray(new String[value.size()]);
        this.handler(new JedisHandler() {
            @Override
            public Object handler(Jedis jedis) {
                return jedis.rpush(_key, _values);
            }
        });
    }

    @Override
    public <T extends Object> void setObjList(String key, List<T> value) throws CacheException {
        final String _key = key;
        final String[] _values = objList2JsonStringList(value);
        this.handler(new JedisHandler() {
            @Override
            public Object handler(Jedis jedis) {
                jedis.del(_key);
                return jedis.rpush(_key, _values);
            }
        });
    }

    @Override
    public void setStringList(String key, List<String> value, int expireSeconds) throws CacheException {
        final String _key = key;
        final int _expireSeconds = expireSeconds;
        final String[] _values = value.toArray(new String[value.size()]);
        this.handler(new JedisHandler() {
            @Override
            public Object handler(Jedis jedis) {
                jedis.del(_key);
                jedis.rpush(_key, _values);
                return jedis.expire(_key, _expireSeconds);
            }
        });
    }

    @Override
    public <T extends Object> void setObjList(String key, List<T> value, int expireSeconds) throws CacheException {
        final String _key = key;
        final String[] _values = objList2JsonStringList(value);
        final int _expireSeconds = expireSeconds;
        this.handler(new JedisHandler() {
            @Override
            public Object handler(Jedis jedis) {
                jedis.del(_key);
                jedis.rpush(_key, _values);
                return jedis.expire(_key, _expireSeconds);
            }
        });
    }

    @Override
    public List<String> getStringList(String key) throws CacheException {
        final String _key = key;
        return this.handler(new JedisHandler() {
            @Override
            public Object handler(Jedis jedis) {
                return jedis.lrange(_key, 0, -1);
            }
        });
    }

    @Override
    public <T> List<T> getObjList(String key, Class<T> classz) throws CacheException {
        final String _key = key;
        List<String> stringValues = this.handler(new JedisHandler() {
            @Override
            public Object handler(Jedis jedis) {
                return jedis.lrange(_key, 0, -1);
            }
        });

        List<T> objs = new ArrayList<T>(stringValues.size());

        String json = null;
        try {
            Iterator<String> iterator = stringValues.iterator();
            while (iterator.hasNext()) {
                json = iterator.next();
                T obj = JsonUtils.parseObject(json, classz);
                objs.add(obj);
            }
        } catch (Exception e) {
            throw new CacheException("将缓存中的数据[" + json + "]转换为" + classz.getName() + "异常", e);
        }

        return objs;
    }

    @Override
    public void pushQueueStringValue(String key, String value) throws CacheException {
        final String _key = key;
        final String _value = value;
        this.handler(new JedisHandler() {
            @Override
            public Object handler(Jedis jedis) {
                return jedis.rpush(_key, _value);
            }
        });
    }

    @Override
    public void pushQueueObjValue(String key, Object value) throws CacheException {
        final String _key = key;
        final String _value = JsonUtils.toJson(value);
        this.handler(new JedisHandler() {
            @Override
            public Object handler(Jedis jedis) {
                return jedis.rpush(_key, _value);
            }
        });
    }

    @Override
    public String popQueueStringValue(String key) throws CacheException {
        final String _key = key;
        return this.handler(new JedisHandler() {
            @Override
            public Object handler(Jedis jedis) {
                return jedis.lpop(_key);
            }
        });
    }

    @Override
    public <T> T popQueueObjValue(String key, Class<T> classz) throws CacheException {

        final String _key = key;
        String json = this.handler(new JedisHandler() {
            @Override
            public Object handler(Jedis jedis) {
                return jedis.lpop(_key);
            }
        });

        if (StringUtils.isBlank(json)) {
            return null;
        }

        try {
            return JsonUtils.parseObject(json, classz);
        } catch (Exception e) {
            throw new CacheException("将缓存中的数据[" + json + "]转换为" + classz.getName() + "异常", e);
        }
    }

    @Override
    public long incrementBy(String key, int num) throws CacheException {
        final String _key = key;
        final int _num = num;
        return this.handler(new JedisHandler() {
            @Override
            public Object handler(Jedis jedis) {
                return jedis.incrBy(_key, _num);
            }
        });
    }

    @Override
    public long incrementMapValueBy(String key, String entryKey, int num) {
        final String _key = key;
        final String _entryKey = entryKey;
        final int _num = num;
        return this.handler(new JedisHandler() {
            @Override
            public Object handler(Jedis jedis) {
                return jedis.hincrBy(_key, _entryKey, _num);
            }
        });
    }

    /**
     * 释放jedis资源放回连接池
     *
     * @param pool
     * @param jedis
     */
    private void returnResourceObject(JedisPool pool, Jedis jedis) {
        if (jedis != null) {
            pool.returnResource(jedis);
        }
    }

    /**
     * 处理redis操作
     *
     * @param jedisHandler
     * @param <T>
     * @return
     */
    private <T> T handler(JedisHandler jedisHandler) {
        JedisPool pool = JedisPoolFactory.getPool();
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedisHandler.handler(jedis);
        } catch (Exception e) {
            throw new CacheException("操作缓存数据异常", e);
        } finally {
            returnResourceObject(pool, jedis);
        }
    }

    /**
     * 将List<Object>转为List<String>
     *
     * @param list
     * @return
     */
    private String[] objList2JsonStringList(List list) {
        Iterator<Object> iterator = list.iterator();
        String[] _values = new String[list.size()];
        int i = 0;
        while (iterator.hasNext()) {
            _values[i] = JsonUtils.toJson(iterator.next());
            i++;
        }
        return _values;
    }

}
