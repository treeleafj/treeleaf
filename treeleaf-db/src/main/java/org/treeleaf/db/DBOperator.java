package org.treeleaf.db;

import java.util.List;

/**
 * @Author leaf
 * 2015/1/16 0016 0:12.
 */
public interface DBOperator {

    /**
     * 执行查询语句
     *
     * @param sql
     * @param params
     * @param modelType
     * @param <T>
     * @return
     */
    <T> List<T> selectBySql(String sql, Object[] params, Class<T> modelType);

    /**
     * 通过sql查询单条数据
     *
     * @param sql
     * @param params
     * @param classz
     * @param <T>
     * @return
     */
    <T> T selectOneBySql(String sql, Object[] params, Class<T> classz);

    /**
     * 执行更新语句
     *
     * @param sql
     * @param params
     * @return
     */
    int updateBySql(String sql, Object[] params);

}
