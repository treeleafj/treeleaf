package org.treeleaf.db.meta;

import org.treeleaf.db.meta.analyzer.DBMetaAnalyzer;
import org.treeleaf.db.meta.analyzer.DBMetaAnalyzerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 元数据工厂,专门提供获取元数据的方法,当没有元数据时提供懒加载
 * Created by leaf on 2015/1/4 0004.
 */
public class DBTableMetaFactory {

    private static Map<String, DBTableMeta> dbTableMetaMap = new HashMap<String, DBTableMeta>();

    /**
     * 根据Model的类型获取对应的表元数据
     *
     * @param classsz
     * @return
     */
    public static DBTableMeta getDBTableMeta(Class<?> classsz) {
        DBTableMeta dbTableMeta = dbTableMetaMap.get(classsz.getName());
        if (dbTableMeta == null) {
            synchronized (classsz) {//保证同种类型进来时的线程同步
                dbTableMeta = dbTableMetaMap.get(classsz.getName());
                if (dbTableMeta == null) {
                    DBMetaAnalyzer dbMetaAnalyzer = DBMetaAnalyzerFactory.getDBMetaAnalyzer();
                    dbTableMeta = dbMetaAnalyzer.analyze(classsz);
                    dbTableMetaMap.put(classsz.getName(), dbTableMeta);
                }
            }
        }
        return dbTableMeta;
    }

}
