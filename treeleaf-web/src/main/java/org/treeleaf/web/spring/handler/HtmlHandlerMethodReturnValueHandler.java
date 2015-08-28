package org.treeleaf.web.spring.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.treeleaf.web.Html;

/**
 * 处理Controller返回Html的结果,底层采用request.forwaed方式进行跳转
 * <p>
 * Created by yaoshuhong on 2015/4/29.
 *
 * @see org.treeleaf.web.Html
 */
public class HtmlHandlerMethodReturnValueHandler implements HandlerMethodReturnValueHandler {

    private static Logger log = LoggerFactory.getLogger(HtmlHandlerMethodReturnValueHandler.class);

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return Html.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public void handleReturnValue(Object returnValue,
                                  MethodParameter returnType, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest) throws Exception {
        //设置为json视图并输出数据
        Html html = (Html) returnValue;
        if (html != null) {

            if (html.getPath() != null) {
                mavContainer.setViewName(html.getPath());
            }

            if (html.getModel() != null) {
                mavContainer.getModel().put("model", html.getModel());
            }
        }
    }

}
