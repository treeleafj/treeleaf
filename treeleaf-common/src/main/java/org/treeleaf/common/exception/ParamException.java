package org.treeleaf.common.exception;

/**
 * 参数异常
 * <p/>
 * Created by yaoshuhong on 2015/7/13.
 */
public class ParamException extends RuntimeException implements BaseException {

    public ParamException(String message) {
        super(message);
    }

    public ParamException(String message, Throwable cause) {
        super(message, cause);
    }
}
