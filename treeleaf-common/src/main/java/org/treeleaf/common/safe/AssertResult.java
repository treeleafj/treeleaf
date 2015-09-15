package org.treeleaf.common.safe;

import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;
import org.treeleaf.common.exception.RetCode;

/**
 * @Author leaf
 * 2015/8/28 0028 1:45.
 */
public class AssertResult {

    private boolean result;

    private String retCode = RetCode.FAIL_PARAM;

    public AssertResult(boolean result) {
        this.result = result;
    }

    public AssertResult setRetCode(String retCode) {
        this.retCode = retCode;
        return this;
    }

    public void throwEx(String msg) {
        if (!result) {
            throw new AssertException(retCode, msg);
        }
    }

    public void throwEx(String msg, Object... param) {
        if (!result) {
            FormattingTuple ft = MessageFormatter.arrayFormat(msg, param);
            throw new AssertException(retCode, ft.getMessage());
        }
    }
}
