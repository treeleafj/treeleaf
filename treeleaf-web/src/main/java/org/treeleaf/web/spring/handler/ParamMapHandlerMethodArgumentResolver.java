package org.treeleaf.web.spring.handler;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.treeleaf.web.ParamMap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 对接口入参Map类型处理
 *
 * @Author leaf
 * 2015/9/4 0004 13:17.
 */
public class ParamMapHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().equals(ParamMap.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        Iterator<String> parameterNames = nativeWebRequest.getParameterNames();
        Map<String, String> param = new HashMap<>();
        while (parameterNames.hasNext()) {
            String name = parameterNames.next();
            param.put(name, nativeWebRequest.getParameter(name));
        }
        return new ParamMap(param);
    }
}
