package org.treeleaf.cache;

import org.treeleaf.common.exception.BaseException;
import org.treeleaf.common.exception.RetCode;
import org.treeleaf.common.exception.RetCodeSupport;

/**
 * 缓存操作异常
 * <p>
 * Created by yaoshuhong on 2015/6/3.
 */
public class CacheException extends BaseException implements RetCodeSupport {

    /**
     * 错误码
     */
    private String retCode = RetCode.FAIL_CACHE;

    public CacheException(String message) {
        super(message);
    }

    public CacheException(String message, Throwable t) {
        super(message, t);
    }

    public CacheException(String retCode, String message) {
        this(message);
        this.retCode = retCode;
    }

    public CacheException(String retCode, String message, Throwable throwable) {
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
