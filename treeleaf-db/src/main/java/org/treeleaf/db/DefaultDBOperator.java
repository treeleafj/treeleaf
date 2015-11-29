package org.treeleaf.db;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.treeleaf.db.handler.AnnotationBeanHandler;
import org.treeleaf.db.handler.AnnotationBeanListHandler;
import org.treeleaf.db.meta.DBTableMeta;
import org.treeleaf.db.meta.DBTableMetaFactory;
import org.treeleaf.db.model.Model;
import org.treeleaf.db.model.example.Example;
import org.treeleaf.db.sql.AnalyzeResult;
import org.treeleaf.db.sql.SqlAnalyzer;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * @Author leaf
 * 2015/1/11 0011 1:00.
 */
public abstract class DefaultDBOperator implements DBModelOperator {

    private static Logger log = LoggerFactory.getLogger(DBModelOperator.class);

    @Override
    public int updateBySql(String sql, Object[] params, Connection connection) {
        QueryRunner queryRunner = new QueryRunner();
        try {
            return queryRunner.update(connection, sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> List<T> selectBySql(String sql, Object[] params, Class<T> modelType, Connection connection) {
        QueryRunner queryRunner = new QueryRunner();
        if (modelType.isAssignableFrom(Model.class)) {
            try {
                return (List<T>) queryRunner.query(connection, sql, new AnnotationBeanListHandler(modelType), params);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
//            return queryRunner.query(connection, sql, new ArrayHandler<T>(), params);
            return null;
        }
    }

    /**
     * 更新指定Id的数据
     *
     * @param model
     * @return
     */
    public boolean update(Object model, Connection connection) {

        if (model == null) {
            log.warn("更新数据失败,传入的model对象为null");
            return false;
        }

        DBTableMeta dbTableMeta = DBTableMetaFactory.getDBTableMeta(model.getClass());
        AnalyzeResult analyzeResult = getSqlAnalyzer().analyzeUpdateByPrimaryKey(dbTableMeta, model);

        log.debug("sql:" + analyzeResult.getSql() + ",param:" + Arrays.toString(analyzeResult.getParams()));

        QueryRunner queryRunner = new QueryRunner();
        try {
            int num = queryRunner.update(connection, analyzeResult.getSql(), analyzeResult.getParams());
            if (num == 1) {
                return true;
            } else if (num == 0) {
                return false;
            } else {//出现致命错误,导致多条数据被修改
                throw new SQLException("数据修改异常,错误的sql与参数导致修改了" + num + "条数据");
            }
        } catch (SQLException e) {
            throw new RuntimeException("修改数据失败", e);
        }
    }

    /**
     * 删除指定个Id的数据
     *
     * @param id
     * @return
     */
    public boolean deleteById(Serializable id, Class classz, Connection connection) {
        DBTableMeta dbTableMeta = DBTableMetaFactory.getDBTableMeta(classz);
        String sql = getSqlAnalyzer().analyzeDeleteByPrimaryKey(dbTableMeta);
        log.debug("sql:" + sql + ",param:" + id);
        QueryRunner queryRunner = new QueryRunner();
        try {
            int num = queryRunner.update(connection, sql, id);
            if (num == 1) {
                return true;
            } else if (num == 0) {
                return false;
            } else {
                throw new SQLException("数据删除异常,错误的sql与参数导致删除了" + num + "条数据");
            }
        } catch (SQLException e) {
            throw new RuntimeException("删除数据失败", e);
        }
    }

    /**
     * 查询指定Id的数据
     *
     * @param id
     * @param classz
     * @param <T>
     * @return
     */
    public <T extends Model> T findById(Serializable id, Class<T> classz, Connection connection) {

        DBTableMeta dbTableMeta = DBTableMetaFactory.getDBTableMeta(classz);
        String sql = getSqlAnalyzer().analyzeSelectByPrimaryKey(dbTableMeta);
        log.debug("sql:" + sql + ",param:" + id);
        QueryRunner queryRunner = new QueryRunner();
        try {
            T obj = queryRunner.query(connection, sql, new AnnotationBeanHandler<T>(classz), id);
            return obj;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据表达式统计长度
     *
     * @param example
     * @param classz
     * @param connection
     * @param <T>
     * @return
     */
    public <T> long countByExample(Example example, Class<T> classz, Connection connection) {
        DBTableMeta dbTableMeta = DBTableMetaFactory.getDBTableMeta(classz);
        AnalyzeResult analyzeResult = getSqlAnalyzer().analyzeCountByExample(dbTableMeta, example);
        log.debug("sql:" + analyzeResult.getSql() + ",param:" + Arrays.toString(analyzeResult.getParams()));
        QueryRunner queryRunner = new QueryRunner();
        try {
            return queryRunner.query(connection, analyzeResult.getSql(), new ScalarHandler<Long>(1), analyzeResult.getParams());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 该方法由子类实现,根据不同的数据库返回不同sql解析器
     *
     * @return
     */
    public abstract SqlAnalyzer getSqlAnalyzer();
}
