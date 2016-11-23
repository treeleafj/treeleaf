package org.treeleaf.db.meta.analyzer;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.treeleaf.db.meta.DBColumnMeta;
import org.treeleaf.db.meta.DBTableMeta;
import org.treeleaf.db.meta.annotation.Column;
import org.treeleaf.db.meta.annotation.Table;
import org.treeleaf.db.model.Model;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * 基于注解方式的元数据解析器
 * Created by leaf on 2015/1/4 0004.
 */
public class AnnotationDBMetaAnalyzer implements DBMetaAnalyzer<Class> {

    private static Logger log = LoggerFactory.getLogger(AnnotationDBMetaAnalyzer.class);

    @Override
    public DBTableMeta analyze(Class param) {
        if (param == null) {
            throw new NullPointerException("解析元数据错误");
        }

        Table tableAnno = (Table) param.getAnnotation(Table.class);
        if (tableAnno == null) {
            throw new RuntimeException("解析元数据错误,未在" + param + "的声明上找到@Table注解");
        }

        //表名称
        String tableName = StringUtils.defaultIfBlank(tableAnno.value(), param.getSimpleName());

        DBTableMeta dbTableMeta = new DBTableMeta();
        dbTableMeta.setName(tableName);
        dbTableMeta.setModelClass(param);

        Field[] fields = param.getDeclaredFields();

        Class superClass = param.getSuperclass();
        while (superClass != null && !superClass.equals(Model.class)) {
            fields = ArrayUtils.addAll(fields, superClass.getDeclaredFields());
            superClass = superClass.getSuperclass();
        }

        for (int i = 0; i < fields.length; i++) {

            Field field = fields[i];
            Column columnAnno = field.getAnnotation(Column.class);

            if (columnAnno != null) {
                //列信息
                DBColumnMeta dbColumnMeta = new DBColumnMeta();
                String columnName = StringUtils.defaultIfBlank(columnAnno.value(), field.getName());
                dbColumnMeta.setName(columnName);
                dbColumnMeta.setAutoIncremen(columnAnno.autoIncremen());
                dbColumnMeta.setPrimaryKey(columnAnno.primaryKey());
                dbColumnMeta.setRequrie(columnAnno.requrie());
                dbColumnMeta.setField(field);


                if (columnAnno.primaryKey()) {
                    dbTableMeta.getPrimaryKeys().add(dbColumnMeta);
                }

                dbTableMeta.getColumnMetas().add(dbColumnMeta);
            }
        }

        return dbTableMeta;
    }
}
