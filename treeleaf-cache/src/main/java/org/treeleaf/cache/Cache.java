package org.treeleaf.cache;

import java.util.List;
import java.util.Map;

/**
 * 线程安全的缓存器,提供缓存操作功能,通过CacheFactory.getCache()可获取其实例
 * <p>
 * Created by yaoshuhong on 2015/6/3.
 *
 * @see CacheFactory
 */
public interface Cache {

    /**
     * 将数据永久缓存,并设定超时时间(秒为单位)
     * <p>
     * 若存在相同key的数据,则会覆盖
     *
     * @param key           缓存中的key
     * @param object        缓存的数据
     * @param expireSeconds 多少秒后超时,只能填一个
     */
    void set(String key, Object object, int... expireSeconds) throws CacheException;

    /**
     * 获取指定key里的缓存数据
     *
     * @param key 缓存中的key
     * @return null或者缓存中的数据
     */
    String get(String key) throws CacheException;

    /**
     * 获取指定key里的缓存数据
     *
     * @param <T>
     * @param key 缓存中的key
     * @return null或者缓存中的数据
     */
    <T> T get(String key, Class<T> classz) throws CacheException;

    /**
     * 通过多个key获取对应的值
     *
     * @param keys   缓存中的key
     * @param classz 要映射的类型, 如果不传则返回的类型为List&lt;String&gt;
     * @param <T>
     * @return
     * @throws CacheException
     */
    <T> List<T> mget(String[] keys, Class<T>... classz) throws CacheException;

    /**
     * 删除指定key的数据
     *
     * @param key 缓存中的key
     * @return 是否删除成功
     */
    boolean del(String key) throws CacheException;

    /**
     * 设置指定key的缓存数据超时时间
     *
     * @param key     缓存中的key
     * @param seconds 超时时间
     * @return 是否设置成功
     */
    boolean expire(String key, int seconds) throws CacheException;

    /**
     * 判断指定的key是否存在于缓存中
     *
     * @param key 缓存中的key
     * @return 是否存在
     */
    boolean exists(String key) throws CacheException;

    /**
     * 将字符串数据永久缓存, 仅当key对应的数据不存在时
     *
     * @param key
     * @param value
     * @throws CacheException
     */
    void setByNoExists(String key, Object value) throws CacheException;

    /**
     * 设置缓存中map里的指定值,仅当map中的file不存在时才有效
     *
     * @param key      缓存中的key
     * @param entryKey
     * @param value    要设置的值
     * @throws CacheException
     */
    void setMapValueByNoExists(String key, String entryKey, String value) throws CacheException;

    /**
     * 将Map数据永久缓存,并设定超时时间(秒为单位)
     *
     * @param key           缓存中的key
     * @param value         缓存的Map数据
     * @param expireSeconds 多少秒后超时,只能填一个
     */
    void setMap(String key, Map<String, String> value, int... expireSeconds) throws CacheException;

    /**
     * 获取指定key里的缓存Map数据
     *
     * @param key 缓存中的key
     */
    Map<String, String> getMap(String key) throws CacheException;

    /**
     * 设置指定缓存中的Map里的指定key
     *
     * @param key        缓存中的key
     * @param entryKey   缓存中Map里要修改值的key
     * @param entryValue 缓存中Map里要修改的值
     */
    void setMapValue(String key, String entryKey, String entryValue) throws CacheException;

    /**
     * 获取指定key里的缓存Map数据中的key为entryKey的数据
     *
     * @param key      缓存中的key
     * @param entryKey 缓存中Map里要修改值的key
     * @return
     */
    String getMapValue(String key, String entryKey) throws CacheException;

    /**
     * 将列表数据永久缓存,并设定超时时间(秒为单位)
     * <p>
     * 若存在相同key的数据,则会覆盖
     *
     * @param key           缓存中的key
     * @param value         要缓存的列表
     * @param expireSeconds 超时时间,只能填一个
     */
    void setStringList(String key, List<String> value, int... expireSeconds) throws CacheException;

    /**
     * 将列表数据永久缓存,并设定超时时间(秒为单位)
     * <p>
     * 若存在相同key的数据,则会覆盖
     *
     * @param key           缓存中的key
     * @param value         要缓存的列表
     * @param expireSeconds 超时时间,只能填一个
     */
    <T extends Object> void setList(String key, List<T> value, int... expireSeconds) throws CacheException;

    /**
     * 获取指定key里的列表缓存数据
     *
     * @param key 缓存中的key
     * @return
     */
    <T> List<T> getList(String key, Class<T>... classz) throws CacheException;

    /**
     * 往缓存中的队列尾部放值
     *
     * @param key   缓存中的key
     * @param value 要放到队列的数据
     */
    void pushQueue(String key, Object value) throws CacheException;

    /**
     * 获取并移除缓存队列的列头
     *
     * @param key    缓存中的key
     * @param classz Object的实际对象
     * @return
     */
    <T> T popQueue(String key, Class<T>... classz) throws CacheException;

    /**
     * 将 key 所存储的值加上增量 num,若key不存在,则初始化为num
     *
     * @param key 缓存中的key
     * @param num 增量值
     * @return
     */
    long incrementBy(String key, int num) throws CacheException;

    /**
     * 将key所存储map中指定key的值加上增量num, 若map不存在则自动创建
     *
     * @param key      缓存中的key
     * @param entryKey map的key
     * @param num      增量值
     * @return
     */
    long incrementMapValueBy(String key, String entryKey, int num);
}
