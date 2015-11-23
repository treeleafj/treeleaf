package org.treeleaf.web.spring.resovler;

import org.treeleaf.common.exception.RetCode;
import org.treeleaf.common.exception.RetCodeSupport;
import org.treeleaf.web.ExtJson;
import org.treeleaf.web.Json;
import org.treeleaf.web.Result;

/**
 * 扩展基本的异常处理器,用于兼容Extjs框架
 *
 * @Author leaf
 * 2015/11/23 0023 0:48.
 */
public class ExtDefaultExceptionHandler extends DefaultExExceptionHandler {

    private String tip = "网络繁忙,请稍后尝试";

    @Override
    public Result invoke(ErrorInfo errorInfo) {

        if (".json".equals(errorInfo.getExt())) {
            Json param1;
            if (errorInfo.getException() instanceof RetCodeSupport) {
                RetCodeSupport support = (RetCodeSupport) errorInfo.getException();
                param1 = new ExtJson(support.getRetCode(), errorInfo.getException().getMessage());
            } else {
                param1 = new ExtJson(RetCode.FAIL_UNKNOWN, this.tip);
            }

            return param1;
        }

        return super.invoke(errorInfo);
    }

    @Override
    public void setTip(String tip) {
        this.tip = tip;
        super.setTip(tip);
    }
}
