package org.treeleaf.web.spring.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.treeleaf.db.ConnectionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 对treeleaf-db所使用的数据库链接清理和释放
 *
 * @author yaoshuhong
 * @date 2014-4-6 下午4:44:12
 */
public class DBConnectionHandlerInterceptor implements HandlerInterceptor {

    protected Logger log = LoggerFactory.getLogger(DBConnectionHandlerInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        //初始化清理数据库链接缓存
        if (handler instanceof HandlerMethod) {
            ConnectionContext.clearCurrentConnections();
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception e)
            throws Exception {

        //释放数据库链接
        if (handler instanceof HandlerMethod) {
            ConnectionContext.closeCurrentConnections();
        }
    }

}
