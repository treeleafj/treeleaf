package org.treeleaf.db;

import org.treeleaf.common.bean.PageResult;
import org.treeleaf.db.model.Model;
import org.treeleaf.db.model.example.Example;

import java.io.Serializable;
import java.util.List;

/**
 * @author leaf
 * @date 2016-11-16 10:13
 */
public interface BaseRepository<T extends Model> {

    Serializable save(T t);

    boolean update(T t);

    boolean updateNotNull(T t);

    boolean delete(String id);

    T findById(Serializable id);

    PageResult<T> findPageByExample(Example example);

    List<T> findByExample(Example example);

    T findOneByExample(Example example);
}
