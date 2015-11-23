package org.treeleaf.web.spring.handler;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.treeleaf.common.bean.ClientInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

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

    // \b 是单词边界(连着的两个(字母字符 与 非字母字符) 之间的逻辑上的间隔),
    // 字符串在编译时会被转码一次,所以是 "\\b"
    // \B 是单词内部逻辑间隔(连着的两个字母字符之间的逻辑上的间隔)
    private final static String phoneReg = "\\b(ip(hone|od)|android|opera m(ob|in)i"
            + "|windows (phone|ce)|blackberry"
            + "|s(ymbian|eries60|amsung)|p(laybook|alm|rofile/midp"
            + "|laystation portable)|nokia|fennec|htc[-_]"
            + "|mobile|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";

    private final static String tableReg = "\\b(ipad|tablet|(Nexus 7)|up.browser"
            + "|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";

    //移动设备正则匹配：手机端、平板
    private final static Pattern phonePat = Pattern.compile(phoneReg, Pattern.CASE_INSENSITIVE);
    private final static Pattern tablePat = Pattern.compile(tableReg, Pattern.CASE_INSENSITIVE);


    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return ClientInfo.class.equals(methodParameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {

        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);

        ClientInfo clientInfo = new ClientInfo();
        if (request != null) {
            clientInfo.setIp(getIp(request));
            clientInfo.setMobile(isMobile(request));
        }

        return clientInfo;
    }

    /**
     * 判断是否移动端
     *
     * @param request
     * @return
     */
    protected boolean isMobile(HttpServletRequest request) {

        if ("0".equals(request.getParameter("_m"))) {
            return false;
        }

        if (StringUtils.isNotBlank(request.getHeader("x-wap-profile"))) {
            return true;
        }

        String userAgent = StringUtils.defaultString(request.getHeader("USER-AGENT").toLowerCase());

        // 匹配
        return phonePat.matcher(userAgent).find() || tablePat.matcher(userAgent).find();
    }

    /**
     * 获取客户端ip
     *
     * @param request
     * @return
     */
    protected String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (StringUtils.isBlank(ip)) {
            ip = request.getRemoteAddr();
        }

        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "127.0.0.1";
        }
        return ip;
    }
}
