package org.treeleaf.db.meta.analyzer;

/**
 * Created by leaf on 2015/1/4 0004.
 */
public class DBMetaAnalyzerFactory {


    public static DBMetaAnalyzer getDBMetaAnalyzer() {
        return new AnnotationDBMetaAnalyzer();
    }
}
