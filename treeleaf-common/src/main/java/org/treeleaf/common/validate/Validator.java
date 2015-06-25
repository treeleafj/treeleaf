package org.treeleaf.common.validate;

/**
 * 验证器统一接口
 * @author yaoshuhong
 * @date 2014-11-5 下午10:11:00
 * @param <T>
 */
public interface Validator<T> {

	boolean validate(T obj);
}
