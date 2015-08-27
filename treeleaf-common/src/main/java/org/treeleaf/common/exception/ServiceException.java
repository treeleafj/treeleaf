package org.treeleaf.common.exception;

/**
 * 调用服务逻辑异常
 * <p/>
 * Created by yaoshuhong on 2015/7/13.
 */
public class ServiceException extends RuntimeException implements BaseException, RetCodeSupport {

    /**
     * 错误码
     */
    private String retCode = RetCode.FAIL_LOGIC;

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String retCode, String message) {
        this(message);
        this.retCode = retCode;
    }

    public ServiceException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public ServiceException(String retCode, String message, Throwable throwable) {
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
