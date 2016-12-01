package org.treeleaf.web.starter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.treeleaf.common.exception.RetCode;
import org.treeleaf.common.exception.RetCodeSupport;
import org.treeleaf.web.Html;
import org.treeleaf.web.Json;
import org.treeleaf.web.Redirect;
import org.treeleaf.web.Result;
import org.treeleaf.web.spring.CommonConstant;
import org.treeleaf.web.spring.Jsonp;
import org.treeleaf.web.spring.resovler.ErrorInfo;
import org.treeleaf.web.spring.view.JsonpView;
import org.treeleaf.web.spring.view.TextView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by leaf on 2016/12/2 0002.
 */
@ControllerAdvice
public class BasicExceptionHandler {

    @Autowired
    private WebStarterConfigurationProperties webStarterConfigurationProperties;

    @ExceptionHandler
    public Result exceptionHandler(Throwable throwable, HttpServletRequest request) {
        //为了在BaseHandlerInterceptor中使用,将真正的异常缓存在request的attr中
        request.setAttribute(CommonConstant.ATTR_CACHE_EXCEPTION, throwable);

        String path = request.getServletPath();
        String ext = getUriExt(path);

        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setException(throwable);
        errorInfo.setUri(request.getRequestURI());
        errorInfo.setExt(ext);

        return exceptionHandler(errorInfo);
    }

    protected Result exceptionHandler(ErrorInfo errorInfo) {
        Map<String, String> model = new HashMap<>();

        if (errorInfo.getException() instanceof RetCodeSupport) {
            model.put("retCode", ((RetCodeSupport) errorInfo.getException()).getRetCode());
            model.put("msg", errorInfo.getException().getMessage());
        } else {
            model.put("retCode", RetCode.FAIL_UNKNOWN);
            model.put("msg", this.getDefaultUnkownExceotionMsg());
        }

        if (".json".equals(errorInfo.getExt())) {
            return new Json(model.get("retCode"), model.get("msg"));
        } else if (".jsonp".equals(errorInfo.getExt())) {
            return new Jsonp(model.get("retCode"), model.get("msg"));
        } else if (this.isRedirect()) {
            return new Redirect(this.getErrorView(), model);
        } else {
            return new Html(this.getErrorView(), model).setRoot(this.htmlModelRoot());
        }
    }

    protected String getUriExt(String path) {
        String ext = "";
        int index = path.lastIndexOf(".");
        if (index > 0) {
            ext = path.substring(index);
        }
        return ext;
    }

    protected String getDefaultUnkownExceotionMsg() {
        return webStarterConfigurationProperties.getErrorTip();
    }

    protected String getErrorView() {
        return webStarterConfigurationProperties.getErrorView();
    }

    protected boolean isRedirect() {
        return webStarterConfigurationProperties.isRedirect();
    }

    protected boolean htmlModelRoot() {
        return false;
    }
}
