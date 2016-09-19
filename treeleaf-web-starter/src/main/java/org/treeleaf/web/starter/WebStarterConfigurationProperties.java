package org.treeleaf.web.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

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
    private boolean errorRedirect = false;

    /**
     * 错误页面地址
     */
    private String errorView = "/error.html";

    /**
     * 页面存放目录
     */
    private String prefix = "/page/";

    /**
     * 页面默认后缀
     */
    private String suffix = ".jsp";

    public String getErrorTip() {
        return errorTip;
    }

    public WebStarterConfigurationProperties setErrorTip(String errorTip) {
        this.errorTip = errorTip;
        return this;
    }

    public boolean isErrorRedirect() {
        return errorRedirect;
    }

    public WebStarterConfigurationProperties setErrorRedirect(boolean errorRedirect) {
        this.errorRedirect = errorRedirect;
        return this;
    }

    public String getErrorView() {
        return errorView;
    }

    public WebStarterConfigurationProperties setErrorView(String errorView) {
        this.errorView = errorView;
        return this;
    }

    public String getSuffix() {
        return suffix;
    }

    public WebStarterConfigurationProperties setSuffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

    public String getPrefix() {
        return prefix;
    }

    public WebStarterConfigurationProperties setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }
}
