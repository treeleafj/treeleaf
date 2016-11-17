package org.treeleaf.db;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
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
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author leaf
 * 2015/1/11 0011 1:00.
 */
public abstract class DefaultDBOperator implements DBModelOperator {

    private static Logger log = LoggerFactory.getLogger(DBModelOperator.class);

    private static Set<Class> baseType = new HashSet() {
        {
            this.add(String.class);
            this.add(Integer.class);
            this.add(Double.class);
            this.add(Date.class);
            this.add(Float.class);
            this.add(Long.class);
        }
    };

    private DBConnectionFactory dbConnectionFactory;

    public DefaultDBOperator(DBConnectionFactory dbConnectionFactory) {
        this.dbConnectionFactory = dbConnectionFactory;
    }

    public DBConnectionFactory getDbConnectionFactory() {
        return dbConnectionFactory;
    }

    public void setDbConnectionFactory(DBConnectionFactory dbConnectionFactory) {
        this.dbConnectionFactory = dbConnectionFactory;
    }

    @Override
    public int updateBySql(String sql, Object[] params) {
        QueryRunner queryRunner = new QueryRunner();

        Connection conn = getDbConnectionFactory().getConnection();

        try {
            return queryRunner.update(conn, sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            getDbConnectionFactory().releaseConnection(conn);
        }
    }

    @Override
    public <T> List<T> selectBySql(String sql, Object[] params, Class<T> modelType) {
        QueryRunner queryRunner = new QueryRunner();

        Connection conn = getDbConnectionFactory().getConnection();

        try {
            if (Model.class.isAssignableFrom(modelType)) {
                return (List<T>) queryRunner.query(conn, sql, new AnnotationBeanListHandler(modelType), params);
            } else if (Map.class.isAssignableFrom(modelType)) {
                return (List<T>) queryRunner.query(conn, sql, new MapListHandler(), params);
            } else {
                throw new RuntimeException("未知的映射类型:" + modelType);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            getDbConnectionFactory().releaseConnection(conn);
        }
    }

    @Override
    public <T> T selectOneBySql(String sql, Object[] params, Class<T> classz) {

        QueryRunner queryRunner = new QueryRunner();

        Connection conn = getDbConnectionFactory().getConnection();

        try {
            if (classz.isAssignableFrom(Model.class)) {
                List<T> list = (List<T>) queryRunner.query(conn, sql, new AnnotationBeanListHandler(classz), params);
                if (list.size() > 0) {
                    return list.get(0);
                }
                return null;

            } else if (baseType.contains(classz)) {
                return queryRunner.query(conn, sql, new ScalarHandler<T>(), params);
            } else {
                Map map = queryRunner.query(conn, sql, new MapHandler(), params);
                return (T) map;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            getDbConnectionFactory().releaseConnection(conn);
        }
    }

    /**
     * 更新指定Id的数据
     *
     * @param model
     * @return
     */
    public boolean update(Object model) {

        if (model == null) {
            log.warn("更新数据失败,传入的model对象为null");
            return false;
        }

        DBTableMeta dbTableMeta = DBTableMetaFactory.getDBTableMeta(model.getClass());
        AnalyzeResult analyzeResult = getSqlAnalyzer().analyzeUpdateByPrimaryKey(dbTableMeta, model);

        return doUpdate(analyzeResult);
    }

    @Override
    public boolean updateNotNull(Object model) {
        if (model == null) {
            log.warn("更新数据失败,传入的model对象为null");
            return false;
        }

        DBTableMeta dbTableMeta = DBTableMetaFactory.getDBTableMeta(model.getClass());
        AnalyzeResult analyzeResult = getSqlAnalyzer().analyzeNotNullUpdateByPrimaryKey(dbTableMeta, model);

        return doUpdate(analyzeResult);
    }

    private boolean doUpdate(AnalyzeResult analyzeResult) {
        printSQL(analyzeResult.getSql(), analyzeResult.getParams());

        Connection conn = getDbConnectionFactory().getConnection();

        QueryRunner queryRunner = new QueryRunner();
        try {
            int num = queryRunner.update(conn, analyzeResult.getSql(), analyzeResult.getParams());
            if (num == 1) {
                return true;
            } else if (num == 0) {
                return false;
            } else {//出现致命错误,导致多条数据被修改
                throw new SQLException("数据修改异常,错误的sql与参数导致修改了" + num + "条数据");
            }
        } catch (SQLException e) {
            throw new RuntimeException("修改数据失败", e);
        } finally {
            getDbConnectionFactory().releaseConnection(conn);
        }
    }

    /**
     * 删除指定个Id的数据
     *
     * @param id
     * @return
     */
    public boolean deleteById(Serializable id, Class classz) {
        DBTableMeta dbTableMeta = DBTableMetaFactory.getDBTableMeta(classz);
        String sql = getSqlAnalyzer().analyzeDeleteByPrimaryKey(dbTableMeta);

        printSQL(sql, new Object[]{id});

        QueryRunner queryRunner = new QueryRunner();

        Connection conn = getDbConnectionFactory().getConnection();

        try {
            int num = queryRunner.update(conn, sql, id);
            if (num == 1) {
                return true;
            } else if (num == 0) {
                return false;
            } else {
                throw new SQLException("数据删除异常,错误的sql与参数导致删除了" + num + "条数据");
            }
        } catch (SQLException e) {
            throw new RuntimeException("删除数据失败", e);
        } finally {
            getDbConnectionFactory().releaseConnection(conn);
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
    public <T extends Model> T findById(Serializable id, Class<T> classz) {

        DBTableMeta dbTableMeta = DBTableMetaFactory.getDBTableMeta(classz);
        String sql = getSqlAnalyzer().analyzeSelectByPrimaryKey(dbTableMeta);

        printSQL(sql, new Object[]{id});

        Connection conn = getDbConnectionFactory().getConnection();

        QueryRunner queryRunner = new QueryRunner();
        try {
            T obj = queryRunner.query(conn, sql, new AnnotationBeanHandler<T>(classz), id);
            return obj;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            getDbConnectionFactory().releaseConnection(conn);
        }
    }

    /**
     * 根据表达式统计长度
     *
     * @param example
     * @param classz
     * @param <T>
     * @return
     */
    public <T> long countByExample(Example example, Class<T> classz) {
        DBTableMeta dbTableMeta = DBTableMetaFactory.getDBTableMeta(classz);
        AnalyzeResult analyzeResult = getSqlAnalyzer().analyzeCountByExample(dbTableMeta, example);

        printSQL(analyzeResult.getSql(), analyzeResult.getParams());

        Connection conn = getDbConnectionFactory().getConnection();

        QueryRunner queryRunner = new QueryRunner();
        try {
            return queryRunner.query(conn, analyzeResult.getSql(), new ScalarHandler<Long>(1), analyzeResult.getParams());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            getDbConnectionFactory().releaseConnection(conn);
        }
    }

    @Override
    public <T> Object sumByExample(Example example, Class<T> classz) {
        DBTableMeta dbTableMeta = DBTableMetaFactory.getDBTableMeta(classz);
        AnalyzeResult analyzeResult = getSqlAnalyzer().analyzeSumByExample(dbTableMeta, example);

        printSQL(analyzeResult.getSql(), analyzeResult.getParams());

        Connection conn = getDbConnectionFactory().getConnection();

        QueryRunner queryRunner = new QueryRunner();
        try {
            T result = queryRunner.query(conn, analyzeResult.getSql(), new ScalarHandler<T>(1), analyzeResult.getParams());
            return result == null ? 0D : result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            getDbConnectionFactory().releaseConnection(conn);
        }
    }

    /**
     * 该方法由子类实现,根据不同的数据库返回不同sql解析器
     *
     * @return
     */
    public abstract SqlAnalyzer getSqlAnalyzer();

    protected void printSQL(String sql, Object[] params) {
        Object[] newParam = new Object[params.length];
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        for (int i = 0; i < params.length; i++) {
            if (params[i] instanceof Date) {
                newParam[i] = dateFormat.format(params[i]);
            } else {
                newParam[i] = params[i];
            }
        }
        log.info("sql:[{}]; param:[{}]", sql, Arrays.toString(newParam));
    }
}
