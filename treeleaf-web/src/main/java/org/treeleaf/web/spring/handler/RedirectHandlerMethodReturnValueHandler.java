package org.treeleaf.web.spring.handler;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.view.RedirectView;
import org.treeleaf.common.http.Http;
import org.treeleaf.web.Redirect;

/**
 * 处理Controller返回Redirect的结果, 底层采用response.sendRedirect的方式处理
 * <p>
 * Created by yaoshuhong on 2015/4/29.
 *
 * @see org.treeleaf.web.Redirect
 */
public class RedirectHandlerMethodReturnValueHandler implements HandlerMethodReturnValueHandler {

    private static Logger log = LoggerFactory.getLogger(RedirectHandlerMethodReturnValueHandler.class);

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return Redirect.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public void handleReturnValue(Object returnValue,
                                  MethodParameter returnType, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest) throws Exception {

        Redirect redirect = (Redirect) returnValue;
        String param = Http.param2UrlParam(redirect.getParam());
        StringBuilder stringBuilder = new StringBuilder(redirect.getPath());
        if (StringUtils.isNotBlank(param)) {
            stringBuilder.append('?');
            stringBuilder.append(param);
        }
        mavContainer.setView(new RedirectView(stringBuilder.toString()));
    }

}
