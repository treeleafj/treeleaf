package org.treeleaf.db;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.treeleaf.common.bean.FastBeanUtils;
import org.treeleaf.db.handler.AnnotationBeanListHandler;
import org.treeleaf.db.meta.DBTableMeta;
import org.treeleaf.db.meta.DBTableMetaFactory;
import org.treeleaf.db.model.Model;
import org.treeleaf.db.model.example.Example;
import org.treeleaf.db.sql.AnalyzeResult;
import org.treeleaf.db.sql.SqlAnalyzer;
import org.treeleaf.db.sql.SqlAnalyzerFactory;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * MySql数据库操作器
 *
 * @Author leaf
 * 2015/1/11 0011 1:02.
 */
public class MySqlDBModelOperator extends DefaultDBOperator {

    private static Logger log = LoggerFactory.getLogger(MySqlDBModelOperator.class);

    protected SqlAnalyzer sqlAnalyzer = null;

    public MySqlDBModelOperator() {
        this.sqlAnalyzer = SqlAnalyzerFactory.getSqlAnalyzer(DBType.MYSQL);
    }

    /**
     * 保存数据
     *
     * @param model
     */
    public void save(Object model, Connection connection) {
        if (model == null) {
            log.warn("更新数据失败,传入的model对象为null");
            return;
        }

        DBTableMeta dbTableMeta = DBTableMetaFactory.getDBTableMeta(model.getClass());
        AnalyzeResult analyzeResult = getSqlAnalyzer().analyzeInsert(dbTableMeta, model);

        log.debug("sql:" + analyzeResult.getSql() + ",param:" + Arrays.toString(analyzeResult.getParams()));

        QueryRunner queryRunner = new QueryRunner();
        try {
            queryRunner.update(connection, analyzeResult.getSql(), analyzeResult.getParams());

            if (dbTableMeta.getPrimaryKeys().size() == 1
                    && dbTableMeta.getPrimaryKeys().get(0).isAutoIncremen()) {
                Object id = queryRunner.query(connection, "SELECT LAST_INSERT_ID()", new ScalarHandler(1));
                Field field = dbTableMeta.getPrimaryKeys().get(0).getField();
                FastBeanUtils.setFieldValue(field, model, id);
            }

        } catch (SQLException e) {
            throw new RuntimeException("保存数据失败", e);
        }

    }

    /**
     * 通过表达式查询数据
     *
     * @param example
     * @param classz
     * @param connection
     * @param <T>
     * @return
     */
    public <T extends Model> List<T> findByExample(Example example, Class<T> classz, Connection connection) {
        DBTableMeta dbTableMeta = DBTableMetaFactory.getDBTableMeta(classz);
        AnalyzeResult analyzeResult = getSqlAnalyzer().analyzeSelectByExample(dbTableMeta, example);
        log.debug("sql:" + analyzeResult.getSql() + ",param:" + Arrays.toString(analyzeResult.getParams()));
        QueryRunner queryRunner = new QueryRunner();
        try {
            return queryRunner.query(connection, analyzeResult.getSql(), new AnnotationBeanListHandler<>(classz), analyzeResult.getParams());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SqlAnalyzer getSqlAnalyzer() {
        return this.sqlAnalyzer;
    }
}
