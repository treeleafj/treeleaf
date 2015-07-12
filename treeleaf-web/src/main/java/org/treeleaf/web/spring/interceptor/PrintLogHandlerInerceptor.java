package org.treeleaf.web.spring.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.treeleaf.web.spring.CommonConstant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * 打印接口访问和耗时,以及异常信息
 * <p/>
 * Created by yaoshuhong on 2015/5/5.
 */
public class PrintLogHandlerInerceptor implements HandlerInterceptor {

    protected Logger log = LoggerFactory.getLogger(PrintLogHandlerInerceptor.class);

    private Class<? extends Exception> logicException = null;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {

            StringBuilder sb = new StringBuilder();
            Enumeration<String> enumeration = request.getParameterNames();
            while (enumeration.hasMoreElements()) {
                String name = enumeration.nextElement();
                sb.append(name);
                sb.append(CommonConstant.URL_SYMBOL_EQUEST);
                sb.append(request.getParameter(name));
                sb.append(CommonConstant.URL_SYMBOL_AND);
            }

            if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
            }

            HandlerMethod hm = (HandlerMethod) handler;
            String classSimpleName = hm.getBean().getClass().getSimpleName();
            request.setAttribute(CommonConstant.TEMP_TIME_NAME, System.currentTimeMillis());
            log.info("开始调用[" + classSimpleName + "." + hm.getMethod().getName() + "]接口, 传入参数:" + sb);
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception e)
            throws Exception {

        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;

            String classSimpleName = hm.getBean().getClass().getSimpleName();
            long t = (Long) request.getAttribute(CommonConstant.TEMP_TIME_NAME);
            long ms = System.currentTimeMillis() - t;

            e = (e != null ? e : (Exception) request.getAttribute(CommonConstant.ATTR_CACHE_EXCEPTION));
            String msg = String.format("结束调用[%s]接口! 用时: %s 毫秒!", classSimpleName + "." + hm.getMethod().getName(), ms);

            if (e != null) {
                if (logicException != null && logicException.isAssignableFrom(e.getClass()) && e.getCause() == null) {
                    msg += String.format(" 出现错误:[%s]", e.getMessage());
                    log.info(msg);
                } else {
                    msg += " 出现异常:";
                    log.error(msg, e);
                }
            } else {
                log.info(msg);
            }
        }
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    public void setLogicException(Class<? extends Exception> logicException) {
        this.logicException = logicException;
    }
}
