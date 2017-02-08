package org.treeleaf.common.http;

import org.treeleaf.common.exception.BaseException;
import org.treeleaf.common.exception.RetCode;
import org.treeleaf.common.exception.RetCodeSupport;

/**
 * Created by yaoshuhong on 2015/6/29.
 */
public class HttpException extends BaseException implements RetCodeSupport {

    private String retCode = RetCode.FAIL_REMOTE;

    public HttpException(String message) {
        super(message);
    }

    public HttpException(String retCode, String message) {
        super(message);
        this.retCode = retCode;
    }

    public HttpException(String message, Throwable t) {
        super(message, t);
    }

    public HttpException(String retCode, String message, Throwable t) {
        this(message, t);
        this.retCode = retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    @Override
    public String getRetCode() {
        return retCode;
    }
}
