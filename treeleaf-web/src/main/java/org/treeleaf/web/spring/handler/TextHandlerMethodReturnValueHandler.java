package org.treeleaf.web.spring.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.treeleaf.web.Text;
import org.treeleaf.web.spring.view.TextView;

/**
 * 处理Controller返回Text的结果
 * <p/>
 * Created by yaoshuhong on 2015/7/19.
 *
 * @see org.treeleaf.web.Text
 */
public class TextHandlerMethodReturnValueHandler implements HandlerMethodReturnValueHandler {

    private static Logger log = LoggerFactory.getLogger(TextHandlerMethodReturnValueHandler.class);

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return Text.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public void handleReturnValue(Object returnValue,
                                  MethodParameter returnType, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest) throws Exception {
        //设置为json视图并输出数据
        Text text = (Text) returnValue;
        if (text != null) {
            mavContainer.setView(new TextView(text));
        }
    }

}
