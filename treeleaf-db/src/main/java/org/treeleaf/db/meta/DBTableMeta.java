package org.treeleaf.db.meta;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库表元信息
 * Created by leaf on 2015/1/4 0004.
 */
public class DBTableMeta extends Meta {

    /**
     * 表名称
     */
    private String name;

    /**
     * 对应的类型
     */
    private Class modelClass;

    /**
     * 主键元数据
     */
    private List<DBColumnMeta> primaryKeys = new ArrayList<DBColumnMeta>();

    /**
     * 字段元数据
     */
    private List<DBColumnMeta> columnMetas = new ArrayList<DBColumnMeta>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DBColumnMeta> getColumnMetas() {
        return columnMetas;
    }

    public void setColumnMetas(List<DBColumnMeta> DBColumnMetas) {
        this.columnMetas = DBColumnMetas;
    }

    public List<DBColumnMeta> getPrimaryKeys() {
        return primaryKeys;
    }

    public void setPrimaryKeys(List<DBColumnMeta> primaryKeys) {
        this.primaryKeys = primaryKeys;
    }

    public Class getModelClass() {
        return modelClass;
    }

    public void setModelClass(Class modelClass) {
        this.modelClass = modelClass;
    }

    @Override
    public String toString() {
        return "DBTableMeta{" +
                "name='" + name + '\'' +
                ", modelClass=" + modelClass +
                ", primaryKeys=" + primaryKeys +
                ", columnMetas=" + columnMetas +
                '}';
    }
}
