package org.treeleaf.db;

import org.treeleaf.db.model.Model;
import org.treeleaf.db.model.example.Example;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;

/**
 * 数据库操作工具
 * Created by leaf on 2015/1/2 0002.
 */
public interface DBModelOperator extends DBOperator {

    /**
     * 保存数据
     *
     * @param model
     */
    void save(Object model, Connection... connection);

    /**
     * 更新指定Id的数据
     *
     * @param model
     * @return
     */
    boolean update(Object model, Connection... connection);

    /**
     * 删除指定个Id的数据
     *
     * @param id
     * @return
     */
    boolean deleteById(Serializable id, Class classz, Connection... connection);

    /**
     * 查询指定Id的数据
     *
     * @param id
     * @param classz
     * @param <T>
     * @return
     */
    <T extends Model> T findById(Serializable id, Class<T> classz, Connection... connection);

    /**
     * 通过表达式查询数据
     *
     * @param example
     * @param classz
     * @param connection
     * @param <T>
     * @return
     */
    <T extends Model> List<T> findByExample(Example example, Class<T> classz, Connection... connection);

    /**
     * 根据表达式统计长度
     *
     * @param example
     * @param classz
     * @param connection
     * @param <T>
     * @return
     */
    <T> long countByExample(Example example, Class<T> classz, Connection... connection);

    /**
     * 统计某个字段的和
     *
     * @param example
     * @param classz
     * @param connection
     * @param <T>
     * @return
     */
    <T> Object sumByExample(Example example, Class<T> classz, Connection... connection);
}
