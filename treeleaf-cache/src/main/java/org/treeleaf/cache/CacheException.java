package org.treeleaf.cache;

import org.treeleaf.common.exception.BaseException;

/**
 * 缓存操作异常
 * <p/>
 * Created by yaoshuhong on 2015/6/3.
 */
public class CacheException extends RuntimeException implements BaseException {

    public CacheException(String message) {
        super(message);
    }

    public CacheException(String message, Throwable cause) {
        super(message, cause);
    }
}
