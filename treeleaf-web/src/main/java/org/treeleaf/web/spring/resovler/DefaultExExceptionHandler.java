package org.treeleaf.web.spring.resovler;

import org.treeleaf.common.exception.BaseException;
import org.treeleaf.common.exception.RetCode;
import org.treeleaf.common.exception.RetCodeSupport;
import org.treeleaf.web.Html;
import org.treeleaf.web.Json;
import org.treeleaf.web.Redirect;
import org.treeleaf.web.Result;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author leaf
 * 2015/8/28 0028 21:11.
 */
public class DefaultExExceptionHandler implements ExExceptionHanlder {

    private String tip = "网络繁忙,请稍后尝试";

    private String errorView = "error";

    private boolean redirect = true;

    @Override
    public Result invoke(ErrorInfo errorInfo) {

        if (".json".equals(errorInfo.getExt())) {//处理json格式的异常
            Json json;
            if (errorInfo.getException() instanceof RetCodeSupport) {
                RetCodeSupport support = (RetCodeSupport) errorInfo.getException();
                json = new Json(support.getRetCode(), errorInfo.getException().getMessage());
            } else {
                json = new Json(RetCode.FAIL_UNKNOWN, tip);
            }
            return json;
        } else {

            Map param = new HashMap<>();
            if (errorInfo.getException() instanceof BaseException) {
                param.put("msg", errorInfo.getException().getMessage());
            } else {
                param.put("msg", tip);
            }

            if (errorInfo.getException() instanceof RetCodeSupport) {
                param.put("retCode", ((RetCodeSupport) errorInfo.getException()).getRetCode());
            } else {
                param.put("retCode", RetCode.FAIL_UNKNOWN);
            }

            if (redirect) {
                return new Redirect(errorView, param);
            } else {
                param.put("exception", errorInfo.getException());
                return new Html(errorView, param);
            }

        }
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public void setErrorView(String errorView) {
        this.errorView = errorView;
    }

    public void setRedirect(boolean redirect) {
        this.redirect = redirect;
    }
}
