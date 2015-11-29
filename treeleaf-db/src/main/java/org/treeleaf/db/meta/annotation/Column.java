package org.treeleaf.db.meta.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标于实体类中的属性上面,用于声明一个字段的信息
 * Created by leaf on 2015/1/2 0002.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {

    /**
     * 数据库表得列名称
     *
     * @return
     */
    String value() default "";

    /**
     * 是否必须不为null和不为空窜空格
     *
     * @return
     */
    boolean requrie() default false;

    /**
     * 是否主键,默认为false
     *
     * @return
     */
    boolean primaryKey() default false;

    /**
     * 是否自动递增(对于例如Mysql的自增主键,会进行多一次查询查出主键值)
     *
     * @return
     */
    boolean autoIncremen() default false;
}
