package org.treeleaf.common.safe;

import org.treeleaf.common.exception.BaseException;
import org.treeleaf.common.exception.RetCode;

/**
 * @Author leaf
 * 2015/6/26 0026 0:36.
 */
public class AssertException extends RuntimeException implements BaseException {

    private String retCode = RetCode.FAIL_PARAM;

    private String message;

    public AssertException(String message) {
        super(message);
        this.message = message;
    }

    public AssertException(String retCode, String message) {
        super(message);
        this.retCode = retCode;
        this.message = message;
    }

    public AssertException(Throwable cause) {
        super(cause);
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
