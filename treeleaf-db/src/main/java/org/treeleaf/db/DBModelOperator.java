package org.treeleaf.db;

import org.treeleaf.common.bean.PageResult;
import org.treeleaf.db.model.Model;
import org.treeleaf.db.model.example.Example;

import java.io.Serializable;
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
    Serializable save(Object model);

    /**
     * 更新指定Id的数据
     *
     * @param model
     * @return
     */
    boolean update(Object model);

    /**
     * 只更新mode中不为null的列
     *
     * @param model
     * @return
     */
    boolean updateNotNull(Object model);

    /**
     * 删除指定个Id的数据
     *
     * @param id
     * @return
     */
    boolean deleteById(Serializable id, Class classz);

    /**
     * 查询指定Id的数据
     *
     * @param id
     * @param classz
     * @param <T>
     * @return
     */
    <T extends Model> T findById(Serializable id, Class<T> classz);

    /**
     * 通过表达式查询数据
     *
     * @param example
     * @param classz
     * @param <T>
     * @return
     */
    <T extends Model> List<T> findByExample(Example example, Class<T> classz);

    /**
     * 通过表达式查询数据,只返回第一条,如果有多条,则抛出异常
     *
     * @param example
     * @param classz
     * @param <T>
     * @return
     */
    default <T extends Model> T findOneByExample(Example example, Class<T> classz) {
        List<T> list = this.findByExample(example, classz);
        if (list.size() > 1) {
            throw new RuntimeException("指定返回一条数据,却查询出" + list.size() + "条数据!!!");
        }
        return list.size() == 1 ? list.get(0) : null;
    }

    /**
     * 查询分页结果
     *
     * @param example
     * @param classz
     * @param <T>
     * @return
     */
    default <T extends Model> PageResult<T> findPageByExample(Example example, Class<T> classz) {
        List<T> list = this.findByExample(example, classz);
        long total = this.countByExample(example, classz);
        return new PageResult<>(example.getPageable(), list, total);
    }

    /**
     * 根据表达式统计长度
     *
     * @param example
     * @param classz
     * @param <T>
     * @return
     */
    <T> long countByExample(Example example, Class<T> classz);

    /**
     * 统计某个字段的和
     *
     * @param example
     * @param classz
     * @param <T>
     * @return
     */
    <T> Object sumByExample(Example example, Class<T> classz);
}
