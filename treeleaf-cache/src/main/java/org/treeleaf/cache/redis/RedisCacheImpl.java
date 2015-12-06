package org.treeleaf.cache.redis;

import org.apache.commons.lang3.StringUtils;
import org.treeleaf.cache.Cache;
import org.treeleaf.cache.CacheException;
import org.treeleaf.common.json.Jsoner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.*;

/**
 * 基于Redis的操作缓存实现
 * <p>
 * Created by yaoshuhong on 2015/6/3.
 */
public class RedisCacheImpl implements Cache {

    @Override
    public void set(String key, Object valud, int... expireSeconds) throws CacheException {
        String v;
        if (valud instanceof String) {
            v = (String) valud;
        } else {
            v = Jsoner.toJson(valud);
        }

        String _key = key;
        String _value = v;

        if (expireSeconds.length > 0) {
            int _expireSeconds = expireSeconds[0];
            this.handler(jedis -> jedis.setex(_key, _expireSeconds, _value));
        } else {
            this.handler(jedis -> jedis.set(_key, _value));
        }

    }

    @Override
    public <T extends Object> T get(String key, Class<T> classz) throws CacheException {
        String json = this.get(key);
        if (StringUtils.isBlank(json)) {
            return null;
        }
        return Jsoner.toObj(json, classz);
    }

    @Override
    public <T> List<T> mget(String[] keys, Class<T>... classz) throws CacheException {

        List<String> list = this.handler(jedis -> jedis.mget(keys));

        if (classz.length > 0) {
            List<T> objList = new ArrayList<>(list.size());
            for (String s : list) {
                if (StringUtils.isBlank(s)) {
                    objList.add(null);
                } else {
                    objList.add(Jsoner.toObj(s, classz[0]));
                }
            }
            return objList;
        }

        return (List<T>) list;
    }

    @Override
    public boolean del(String key) throws CacheException {
        String _key = key;
        return this.handler(jedis -> {
            long r = jedis.del(_key);
            return r > 0;
        });
    }

    @Override
    public boolean expire(String key, int seconds) throws CacheException {
        String _key = key;
        int _seconds = seconds;
        return this.handler(jedis -> jedis.expire(_key, _seconds) > 0 ? true : false);

    }

    @Override
    public boolean exists(String key) throws CacheException {
        String _key = key;
        return this.handler(jedis -> jedis.exists(_key));
    }

    @Override
    public void setByNoExists(String key, Object value) throws CacheException {

        String v;
        if (value instanceof String) {
            v = (String) value;
        } else {
            v = Jsoner.toJson(value);
        }

        String _key = key;
        String _value = v;
        this.handler(jedis -> jedis.setnx(_key, _value));
    }

    @Override
    public void setMapValueByNoExists(String key, String entryKey, String value) throws CacheException {
        String _key = key;
        String _entryKey = entryKey;
        String _value = value;
        this.handler(jedis -> jedis.hsetnx(_key, _entryKey, _value));
    }

    @Override
    public String get(String key) throws CacheException {
        String _key = key;
        return this.handler(jedis -> jedis.get(_key));
    }

    @Override
    public void setMap(String key, Map<String, String> value, int... expireSeconds) throws CacheException {

        String _key = key;
        Map<String, String> _value = value;

        if (expireSeconds.length > 0) {
            int _expireSeconds = expireSeconds[0];
            this.handler(jedis -> {
                jedis.hmset(_key, _value);
                return jedis.expire(_key, _expireSeconds);
            });
        } else {
            this.handler(jedis -> jedis.hmset(_key, _value));
        }
    }

    @Override
    public Map<String, String> getMap(String key) throws CacheException {
        String _key = key;
        return this.handler(jedis -> jedis.hgetAll(_key));
    }

    @Override
    public void setMapValue(String key, String entryKey, String entryValue) throws CacheException {
        String _key = key;
        String _entryKey = entryKey;
        String _entryValue = entryValue;
        this.handler(jedis -> jedis.hset(_key, _entryKey, _entryValue));
    }

    @Override
    public String getMapValue(String key, String entryKey) throws CacheException {
        String _key = key;
        String _entryKey = entryKey;
        return this.handler(jedis -> jedis.hget(_key, _entryKey));
    }

    @Override
    public void setStringList(String key, List<String> value, int... expireSeconds) throws CacheException {
        String _key = key;
        String[] _values = value.toArray(new String[value.size()]);

        if (expireSeconds.length > 0) {
            int _expireSeconds = expireSeconds[0];
            this.handler(jedis -> {
                jedis.del(_key);
                jedis.rpush(_key, _values);
                return jedis.expire(_key, _expireSeconds);
            });
        } else {
            this.handler(jedis -> {
                jedis.del(_key);
                return jedis.rpush(_key, _values);
            });
        }
    }

    @Override
    public <T extends Object> void setList(String key, List<T> value, int... expireSeconds) throws CacheException {
        String _key = key;

        Iterator<T> iterator = value.iterator();
        String[] array = new String[value.size()];
        int i = 0;
        while (iterator.hasNext()) {
            T next = iterator.next();
            if (next instanceof String) {
                array[i] = (String) next;
            } else {
                array[i] = Jsoner.toJson(next);
            }
            i++;
        }

        String[] _values = array;

        if (expireSeconds.length > 0) {
            int _expireSeconds = expireSeconds[0];
            this.handler(jedis -> {
                jedis.del(_key);
                jedis.rpush(_key, _values);
                return jedis.expire(_key, _expireSeconds);
            });
        } else {
            this.handler(jedis -> {
                jedis.del(_key);
                return jedis.rpush(_key, _values);
            });
        }
    }

    @Override
    public <T> List<T> getList(String key, Class<T>... classz) throws CacheException {
        String _key = key;

        List stringValues = this.handler(jedis -> jedis.lrange(_key, 0, -1));

        if (classz.length <= 0) {
            return stringValues;
        } else {

            List<T> objs = new ArrayList<T>(stringValues.size());

            String json = null;
            try {
                Iterator<String> iterator = stringValues.iterator();
                while (iterator.hasNext()) {
                    json = iterator.next();
                    T obj = Jsoner.toObj(json, classz[0]);
                    objs.add(obj);
                }
            } catch (Exception e) {
                throw new CacheException("将缓存中的数据[" + json + "]转换为" + classz[0].getName() + "异常", e);
            }

            return objs;
        }

    }

    @Override
    public void pushQueue(String key, Object value) throws CacheException {
        String v;
        if (value instanceof String) {
            v = (String) value;
        } else {
            v = Jsoner.toJson(value);
        }

        String _key = key;
        String _value = v;
        this.handler(jedis -> jedis.rpush(_key, _value));
    }

    @Override
    public <T> T popQueue(String key, Class<T>... classz) throws CacheException {

        String _key = key;
        String r = this.handler(jedis -> jedis.lpop(_key));

        if (classz.length <= 0) {
            return (T) r;
        }

        if (StringUtils.isBlank(r)) {
            return null;
        }

        try {
            return Jsoner.toObj(r, classz[0]);
        } catch (Exception e) {
            throw new CacheException("将缓存中的数据[" + r + "]转换为" + classz[0].getName() + "异常", e);
        }
    }

    @Override
    public long incrementBy(String key, int num) throws CacheException {
        String _key = key;
        int _num = num;
        return this.handler(jedis -> jedis.incrBy(_key, _num));
    }

    @Override
    public long incrementMapValueBy(String key, String entryKey, int num) {
        String _key = key;
        String _entryKey = entryKey;
        int _num = num;
        return this.handler(jedis -> jedis.hincrBy(_key, _entryKey, _num));
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
            return (T) jedisHandler.handler(jedis);
        } catch (Exception e) {
            throw new CacheException("操作缓存异常", e);
        } finally {
            if (jedis != null) {//释放jedis资源放回连接池
//                pool.returnResource(jedis);
                jedis.close();
            }
        }
    }
}
