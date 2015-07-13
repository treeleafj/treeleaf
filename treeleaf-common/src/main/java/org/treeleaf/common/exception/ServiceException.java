package org.treeleaf.common.exception;

/**
 * 调用服务逻辑异常
 * <p/>
 * Created by yaoshuhong on 2015/7/13.
 */
public class ServiceException extends RuntimeException implements BaseException {

    /**
     * 错误码
     */
    private String retCode;

    public ServiceException(String retCode) {
        super();
        this.retCode = retCode;
    }

    public ServiceException(String retCode, String message) {
        super(message);
        this.retCode = retCode;
    }

    public ServiceException(String retCode, String message, Throwable throwable) {
        super(message, throwable);
        this.retCode = retCode;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }
}
