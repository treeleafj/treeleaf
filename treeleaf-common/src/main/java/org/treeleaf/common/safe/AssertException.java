package org.treeleaf.common.safe;

import org.treeleaf.common.exception.BaseException;
import org.treeleaf.common.exception.RetCode;
import org.treeleaf.common.exception.RetCodeSupport;

/**
 * @Author leaf
 * 2015/6/26 0026 0:36.
 */
public class AssertException extends BaseException implements RetCodeSupport {

    private String retCode = RetCode.FAIL_PARAM;

    public AssertException(String message) {
        super(message);
    }

    public AssertException(String retCode, String message) {
        super(message);
        this.retCode = retCode;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

}
