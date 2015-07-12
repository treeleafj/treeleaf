package org.treeleaf.web.spring.resovler;

/**
 * 转换器
 * <p/>
 * Created by yaoshuhong on 2015/5/29.
 */
public interface Converter<S> {


    /**
     * 是否支持转换
     *
     * @param object
     * @return
     */
    boolean support(Object object);

    /**
     * 对象转换
     *
     * @param source
     * @return
     */
    Object convert(S source);

}
