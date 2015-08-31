package org.treeleaf.web.spring.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.treeleaf.common.exception.BaseException;
import org.treeleaf.common.exception.RetCodeSupport;
import org.treeleaf.web.spring.CommonConstant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * 打印接口访问,入参和耗时,以及异常信息
 * <p>
 * Created by yaoshuhong on 2015/5/5.
 */
public class PrintLogHandlerInerceptor implements HandlerInterceptor {

    protected Logger log = LoggerFactory.getLogger(PrintLogHandlerInerceptor.class);

    private final static char URL_SYMBOL_EQUEST = '=';

    private final static char URL_SYMBOL_AND = '&';

    private boolean accessLog = true;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        if (handler instanceof HandlerMethod) {

            request.setAttribute(CommonConstant.TEMP_TIME_NAME, System.currentTimeMillis());

            if (!accessLog) { //如果不打印就直接返回
                return true;
            }

            StringBuilder sb = new StringBuilder();
            Enumeration<String> enumeration = request.getParameterNames();
            while (enumeration.hasMoreElements()) {
                String name = enumeration.nextElement();
                sb.append(name);
                sb.append(URL_SYMBOL_EQUEST);
                sb.append(request.getParameter(name));
                sb.append(URL_SYMBOL_AND);
            }

            if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
            }

            HandlerMethod hm = (HandlerMethod) handler;
            String className = hm.getBean().getClass().getSimpleName();
            log.info("开始调用[{}][{}.{}]接口, 传入参数:{}", request.getServletPath(), className, hm.getMethod().getName(), sb);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) throws Exception {

        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;

            String classSimpleName = hm.getBean().getClass().getSimpleName();
            long t = (Long) request.getAttribute(CommonConstant.TEMP_TIME_NAME);
            long ms = System.currentTimeMillis() - t;

            e = (e != null ? e : (Exception) request.getAttribute(CommonConstant.ATTR_CACHE_EXCEPTION));
            String msg = String.format("结束调用[%s][%s]接口! 用时: %s 毫秒!", request.getRequestURI(), classSimpleName + "." + hm.getMethod().getName(), ms);

            if (e != null) {
                if (e instanceof BaseException && e.getCause() == null) {
                    if (e instanceof RetCodeSupport) {
                        msg += String.format(" 出现错误:[%s][%s]", ((RetCodeSupport) e).getRetCode(), e.getMessage());
                    } else {
                        msg += String.format(" 出现错误:[%s]", e.getMessage());
                    }
                    log.info(msg);
                } else {
                    msg += " 出现异常:";
                    log.error(msg, e);
                }
            } else if (accessLog) {
                log.info(msg);
            }
        }
    }

    public void setAccessLog(boolean accessLog) {
        this.accessLog = accessLog;
    }
}
