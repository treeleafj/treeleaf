package org.treeleaf.db;

import java.sql.Connection;
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
     * @param connection
     * @param <T>
     * @return
     */
    <T> List<T> selectBySql(String sql, Object[] params, Class<T> modelType, Connection connection);

    /**
     * 执行更新语句
     *
     * @param sql
     * @param params
     * @param connection
     * @return
     */
    int updateBySql(String sql, Object[] params, Connection connection);

}
