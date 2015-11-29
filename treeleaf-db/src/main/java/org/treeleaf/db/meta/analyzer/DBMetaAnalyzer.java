package org.treeleaf.db.meta.analyzer;

import org.treeleaf.db.meta.DBTableMeta;

/**
 * 元数据解析器
 * Created by leaf on 2015/1/4 0004.
 */
public interface DBMetaAnalyzer<T> {

    DBTableMeta analyze(T param);

}
