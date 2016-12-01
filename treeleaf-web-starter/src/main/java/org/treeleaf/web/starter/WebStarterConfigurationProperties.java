package org.treeleaf.web.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.treeleaf.web.spring.resovler.ExExceptionHanlder;
import org.treeleaf.web.spring.resovler.ExtDefaultExceptionHandler;

/**
 * web端配置
 *
 * @author yaoshuhong
 * @date 2016-09-14 12:50
 */
@Component
@ConfigurationProperties("treeleaf.web")
public class WebStarterConfigurationProperties {

    /**
     * 默认错误提示语
     */
    private String errorTip = "网络繁忙,请稍后尝试";

    /**
     * 发生错误时,页面是否采用redirect方式重定向到错误页面
     */
    private boolean redirect = false;

    /**
     * 错误页面地址
     */
    private String errorView = "/error.html";

    private Class<? extends ExExceptionHanlder> exceptionHanlderClass = ExtDefaultExceptionHandler.class;

    public String getErrorTip() {
        return errorTip;
    }

    public WebStarterConfigurationProperties setErrorTip(String errorTip) {
        this.errorTip = errorTip;
        return this;
    }

    public boolean isRedirect() {
        return redirect;
    }

    public WebStarterConfigurationProperties setRedirect(boolean redirect) {
        this.redirect = redirect;
        return this;
    }

    public String getErrorView() {
        return errorView;
    }

    public WebStarterConfigurationProperties setErrorView(String errorView) {
        this.errorView = errorView;
        return this;
    }

    public Class<? extends ExExceptionHanlder> getExceptionHanlderClass() {
        return exceptionHanlderClass;
    }

    public WebStarterConfigurationProperties setExceptionHanlderClass(Class<? extends ExExceptionHanlder> exceptionHanlderClass) {
        this.exceptionHanlderClass = exceptionHanlderClass;
        return this;
    }
}
