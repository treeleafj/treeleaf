package org.treeleaf.db.sql;

/**
 * sql解析结果,存放着sql和参数
 * Created by leaf on 2015/1/4 0004.
 */
public class AnalyzeResult {

    private String sql;

    private Object[] params;

    public AnalyzeResult() {
        this.params = new Object[]{};
    }

    public AnalyzeResult(String sql) {
        this();
        this.sql = sql;
    }

    public AnalyzeResult(String sql, Object[] params) {
        this.sql = sql;
        this.params = params;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }
}
