package org.treeleaf.wechat.pay;

import org.treeleaf.common.exception.ServiceException;

/**
 * 微信支付异常
 *
 * @Author leaf
 * 2016/3/11 0011 21:15.
 */
public class WechatPayException extends ServiceException {


    public WechatPayException(String message) {
        super(message);
    }

    public WechatPayException(String retCode, String message) {
        super(retCode, message);
    }

    public WechatPayException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public WechatPayException(String retCode, String message, Throwable throwable) {
        super(retCode, message, throwable);
    }
}
