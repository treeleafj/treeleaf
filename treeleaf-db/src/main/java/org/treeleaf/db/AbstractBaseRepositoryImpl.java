package org.treeleaf.db;

import org.treeleaf.common.bean.ClassUtils;
import org.treeleaf.common.bean.PageResult;
import org.treeleaf.db.model.Model;
import org.treeleaf.db.model.example.Example;

import java.io.Serializable;
import java.util.List;

/**
 * 基础Repository实现
 *
 * @author leaf
 * @date 2016-11-16 10:16
 */
public abstract class AbstractBaseRepositoryImpl<T extends Model> implements BaseRepository<T> {

    private Class<T> modelType;

    public AbstractBaseRepositoryImpl() {
        modelType = ClassUtils.getGeneric(this.getClass());
    }

    @Override
    public Serializable save(T t) {
        return getDbModelOperator().save(t);
    }

    @Override
    public boolean update(T t) {
        return getDbModelOperator().update(t);
    }

    @Override
    public boolean updateNotNull(T t) {
        return getDbModelOperator().updateNotNull(t);
    }

    @Override
    public boolean delete(String id) {
        return getDbModelOperator().deleteById(id, modelType);
    }

    @Override
    public T findById(Serializable id) {
        return getDbModelOperator().findById(id, modelType);
    }

    @Override
    public PageResult<T> findPageByExample(Example example) {
        return getDbModelOperator().findPageByExample(example, modelType);
    }

    @Override
    public List<T> findByExample(Example example) {
        return getDbModelOperator().findByExample(example, modelType);
    }

    @Override
    public T findOneByExample(Example example) {
        return getDbModelOperator().findOneByExample(example, modelType);
    }

    public abstract DBModelOperator getDbModelOperator();
}
