package org.treeleaf.web.spring.handler;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.treeleaf.web.Json;
import org.treeleaf.web.spring.view.FastJsonView;

/**
 * 处理Controller返回Json的结果,底层将其转为json字符串再输出
 * <p/>
 * Created by yaoshuhong on 2015/4/29.
 *
 * @see org.treeleaf.web.Json
 */
public class JsonHandlerMethodReturnValueHandler implements HandlerMethodReturnValueHandler {

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return Json.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public void handleReturnValue(Object returnValue,
                                  MethodParameter returnType, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest) throws Exception {
        //设置为json视图并输出数据
        mavContainer.setView(new FastJsonView(returnValue));
    }
}
