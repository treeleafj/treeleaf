package org.treeleaf.web.spring.handler;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.treeleaf.web.ClientInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * 客户端信息处理
 * <p>
 * 专门处理方法入参上的ClientInfo类型的构建
 *
 * @Author leaf
 * 2015/9/4 0004 18:52.
 */
public class ClientInfoHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private static Logger log = LoggerFactory.getLogger(ClientInfoHandlerMethodArgumentResolver.class);

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return ClientInfo.class.equals(methodParameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        String ip = nativeWebRequest.getHeader("X-Real-IP");
        if (StringUtils.isBlank(ip)) {
            HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
            if (request != null) {
                ip = request.getRemoteAddr();
            }
        }

        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "127.0.0.1";
        }

        ClientInfo clientInfo = new ClientInfo();
        clientInfo.setIp(ip);

        return clientInfo;
    }
}
