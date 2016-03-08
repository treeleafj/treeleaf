package org.treeleaf.web;

import java.lang.annotation.*;

/**
 * 实现在Controller的方法入参中获取授权通过的客户信息
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginUser {

    /**
     * 必须不为空(当为true时,客户若未登录或不存在,则会抛出异常出去,强制登录,若为false,则不会抛出异常,获取的User对象可能为null)
     *
     * @return
     */
    boolean require() default true;

}
