package org.treeleaf.common.http;

/**
 * Created by yaoshuhong on 2015/6/29.
 */
public class HttpException extends RuntimeException{

    public HttpException(String message) {
        super(message);
    }

    public HttpException(String message, Throwable cause) {
        super(message, cause);
    }
}
