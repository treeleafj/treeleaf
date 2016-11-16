package org.treeleaf.db.sql;

import org.treeleaf.db.meta.DBTableMeta;
import org.treeleaf.db.model.example.Example;

/**
 * sql解析器接口
 * <p>声明通用sql的解析</p>
 * Created by leaf on 2015/1/4 0004.
 */
public interface SqlAnalyzer {

    /**
     * 解析通过主键查询数据的sql
     *
     * @param dbTableMeta
     * @return
     */
    String analyzeSelectByPrimaryKey(DBTableMeta dbTableMeta);

    /**
     * 解析查询语句
     *
     * @param dbTableMeta
     * @param example
     * @return
     */
    AnalyzeResult analyzeSelectByExample(DBTableMeta dbTableMeta, Example example);

    /**
     * 解析通过主键删除数据的sql
     *
     * @param dbTableMeta
     * @return
     */
    String analyzeDeleteByPrimaryKey(DBTableMeta dbTableMeta);

    /**
     * 解析通过主键修改数据的sql
     *
     * @param dbTableMeta
     * @param model
     * @return
     */
    AnalyzeResult analyzeUpdateByPrimaryKey(DBTableMeta dbTableMeta, Object model);

    /**
     * 解析插入数据的sql
     *
     * @param dbTableMeta
     * @param model
     * @return
     */
    AnalyzeResult analyzeInsert(DBTableMeta dbTableMeta, Object model);

    /**
     * 解析统计总记录数的语句
     *
     * @param dbTableMeta
     * @param example
     * @return
     */
    AnalyzeResult analyzeCountByExample(DBTableMeta dbTableMeta, Example example);

    /**
     * 解析统计字段之和的语句
     *
     * @param dbTableMeta
     * @param example
     * @return
     */
    AnalyzeResult analyzeSumByExample(DBTableMeta dbTableMeta, Example example);

    /**
     * 解析通过主键修改非null数据的sql
     *
     * @param dbTableMeta
     * @param model
     * @return
     */
    AnalyzeResult analyzeNotNullUpdateByPrimaryKey(DBTableMeta dbTableMeta, Object model);
}
