package org.treeleaf.db;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.treeleaf.common.bean.FastBeanUtils;
import org.treeleaf.common.safe.ID;
import org.treeleaf.db.handler.AnnotationBeanListHandler;
import org.treeleaf.db.meta.DBColumnMeta;
import org.treeleaf.db.meta.DBTableMeta;
import org.treeleaf.db.meta.DBTableMetaFactory;
import org.treeleaf.db.model.Model;
import org.treeleaf.db.model.example.Example;
import org.treeleaf.db.sql.AnalyzeResult;
import org.treeleaf.db.sql.SqlAnalyzer;
import org.treeleaf.db.sql.SqlAnalyzerFactory;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * MySql数据库操作器
 *
 * @Author leaf
 * 2015/1/11 0011 1:02.
 */
public class MySqlDBModelOperator extends DefaultDBOperator {

    private Logger log = LoggerFactory.getLogger(MySqlDBModelOperator.class);

    protected SqlAnalyzer sqlAnalyzer = null;

    public MySqlDBModelOperator(DBConnectionFactory dbConnectionFactory) {
        super(dbConnectionFactory);
        this.sqlAnalyzer = SqlAnalyzerFactory.getSqlAnalyzer(DBType.MYSQL);
    }

    /**
     * 保存数据
     *
     * @param model
     */
    public Serializable save(Object model) {
        if (model == null) {
            log.warn("更新数据失败,传入的model对象为null");
            return null;
        }

        DBTableMeta dbTableMeta = DBTableMetaFactory.getDBTableMeta(model.getClass());
        DBColumnMeta pk = dbTableMeta.getPrimaryKeys().get(0);
        if (dbTableMeta.getPrimaryKeys().size() == 1) {
            if (FastBeanUtils.getFieldValue(pk.getField(), model) == null && !pk.isAutoIncremen()) {//非数据库自增的主键,如果没传,则自动生成一个
                FastBeanUtils.setFieldValue(pk.getField(), model, ID.get());
            }
        }

        AnalyzeResult analyzeResult = getSqlAnalyzer().analyzeInsert(dbTableMeta, model);

        printSQL(analyzeResult.getSql(), analyzeResult.getParams());

        Connection conn = getDbConnectionFactory().getConnection();

        QueryRunner queryRunner = new QueryRunner();
        try {
            queryRunner.update(conn, analyzeResult.getSql(), analyzeResult.getParams());

            if (dbTableMeta.getPrimaryKeys().size() == 1 && pk.isAutoIncremen()) {//对于基于mysql数据库自增的主键,这里查出来,并设值回去
                Object id = queryRunner.query(conn, "SELECT LAST_INSERT_ID()", new ScalarHandler(1));
                FastBeanUtils.setFieldValue(pk.getField(), model, id);
                return (Serializable) id;
            } else {
                return (Serializable) FastBeanUtils.getFieldValue(pk.getField(), model);
            }

        } catch (SQLException e) {
            throw new RuntimeException("保存数据失败", e);
        } finally {
            getDbConnectionFactory().releaseConnection(conn);
        }

    }

    /**
     * 通过表达式查询数据
     *
     * @param example
     * @param classz
     * @param <T>
     * @return
     */
    public <T extends Model> List<T> findByExample(Example example, Class<T> classz) {
        DBTableMeta dbTableMeta = DBTableMetaFactory.getDBTableMeta(classz);
        AnalyzeResult analyzeResult = getSqlAnalyzer().analyzeSelectByExample(dbTableMeta, example);
        printSQL(analyzeResult.getSql(), analyzeResult.getParams());

        Connection conn = getDbConnectionFactory().getConnection();

        QueryRunner queryRunner = new QueryRunner();
        try {
            return queryRunner.query(conn, analyzeResult.getSql(), new AnnotationBeanListHandler<>(classz), analyzeResult.getParams());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            getDbConnectionFactory().releaseConnection(conn);
        }
    }

    @Override
    public SqlAnalyzer getSqlAnalyzer() {
        return this.sqlAnalyzer;
    }

}
