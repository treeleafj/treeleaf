package org.treeleaf.common.exception;

/**
 * 参数异常
 * <p/>
 * Created by yaoshuhong on 2015/7/13.
 */
public class ParamException extends RuntimeException implements BaseException, RetCodeSupport {

    /**
     * 错误码
     */
    private String retCode = RetCode.FAIL_PARAM;

    public ParamException(String message) {
        super(message);
    }

    public ParamException(String retCode, String message) {
        this(message);
        this.retCode = retCode;
    }

    public ParamException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public ParamException(String retCode, String message, Throwable throwable) {
        this(message, throwable);
        this.retCode = retCode;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

}
