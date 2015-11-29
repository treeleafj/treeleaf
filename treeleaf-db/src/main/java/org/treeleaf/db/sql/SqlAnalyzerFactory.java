package org.treeleaf.db.sql;

import org.treeleaf.db.DBType;

/**
 * sql解析器工厂
 * Created by leaf on 2015/1/4 0004.
 */
public class SqlAnalyzerFactory {

    /**
     * 获取Sql解析器
     *
     * @return
     */
    public static SqlAnalyzer getSqlAnalyzer() {
        return new MySqlAnalyzerImpl();
    }

    public static SqlAnalyzer getSqlAnalyzer(DBType dbType) {
        if (dbType == DBType.MYSQL) {
            return new MySqlAnalyzerImpl();
        }
        throw new IllegalArgumentException("找不到" + dbType + "对象的SqlAnalyzer");
    }
}
