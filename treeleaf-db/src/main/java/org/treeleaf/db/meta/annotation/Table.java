package org.treeleaf.db.meta.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标于实体类名上面,用于声明一个表的信息
 * Created by leaf on 2015/1/2 0002.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {

    /**
     * 表名
     *
     * @return
     */
    String value();

}
