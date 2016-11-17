package org.treeleaf.db.handler;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.treeleaf.common.bean.FastBeanUtils;
import org.treeleaf.db.meta.DBColumnMeta;
import org.treeleaf.db.meta.DBTableMeta;
import org.treeleaf.db.meta.analyzer.DBMetaAnalyzer;
import org.treeleaf.db.meta.analyzer.DBMetaAnalyzerFactory;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author leaf
 * 2015/1/8 0008 21:46.
 */
public class AnnotationRowProcessor extends BasicRowProcessor {

    private static DBMetaAnalyzer dbMetaAnalyzer = DBMetaAnalyzerFactory.getDBMetaAnalyzer();

    @Override
    public <T> T toBean(ResultSet rs, Class<T> type) throws SQLException {
        DBTableMeta dbTableMeta = dbMetaAnalyzer.analyze(type);
        Map<String, DBColumnMeta> stringDBColumnMetaHashMap = new HashMap<String, DBColumnMeta>();
        for (DBColumnMeta dbColumnMeta : dbTableMeta.getColumnMetas()) {
            stringDBColumnMetaHashMap.put(dbColumnMeta.getName().toUpperCase(), dbColumnMeta);
        }

        Map<String, Object> result = new HashMap<String, Object>();
        ResultSetMetaData rsmd = rs.getMetaData();
        int cols = rsmd.getColumnCount();

        for (int i = 1; i <= cols; i++) {
            String columnName = rsmd.getColumnLabel(i);
            if (null == columnName || 0 == columnName.length()) {
                columnName = rsmd.getColumnName(i);
            }

            DBColumnMeta dbColumnMeta = stringDBColumnMetaHashMap.get(columnName.toUpperCase());
            if (dbColumnMeta == null) {
                continue;
            }

            result.put(dbColumnMeta.getField().getName(), rs.getObject(i));
        }

        try {
            return (T) FastBeanUtils.fastPopulate(type, result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> List<T> toBeanList(ResultSet rs, Class<T> type) throws SQLException {
        List<T> results = new ArrayList<T>();

        while (rs.next()) {
            results.add(this.toBean(rs, type));
        }
        return results;
    }

}
