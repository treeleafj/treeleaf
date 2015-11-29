package org.treeleaf.db.meta;

import java.lang.reflect.Field;

/**
 * 字段元数据
 * Created by leaf on 2015/1/4 0004.
 */
public class DBColumnMeta extends Meta {

    /**
     * 列名称
     */
    private String name;

    /**
     * 是否主键
     */
    private boolean primaryKey = false;

    /**
     * 是否自动递增(对于例如Mysql的自增主键,会进行多一次查询查出主键值)
     */
    private boolean autoIncremen = false;

    /**
     * 是否必须不为null和不为空窜空格
     */
    private boolean requrie;

    /**
     * 对应的java类中属性
     */
    private Field field;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public boolean isRequrie() {
        return requrie;
    }

    public void setRequrie(boolean requrie) {
        this.requrie = requrie;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public boolean isAutoIncremen() {
        return autoIncremen;
    }

    public void setAutoIncremen(boolean autoIncremen) {
        this.autoIncremen = autoIncremen;
    }

    @Override
    public String toString() {
        return "ColumnMeta{" +
                "name='" + name + '\'' +
                ", primaryKey=" + primaryKey +
                ", autoIncremen=" + autoIncremen +
                ", requrie=" + requrie +
                ", field=" + field +
                '}';
    }
}
