package org.treeleaf.web.spring.resovler;

/**
 * 异常转换器,怎么处理继承Throwable的异常转换
 * <p/>
 * Created by yaoshuhong on 2015/6/1.
 */
public interface ExceptionConverter<T extends Throwable> extends Converter<T> {
}
