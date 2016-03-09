package org.treeleaf.web.spring.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 提供聚合读个HandlerInterceptor的适配方法
 * <p>
 * Created by yaoshuhong on 2015/5/5.
 */
public class MultipleHandlerInerceptor implements HandlerInterceptor {

    private List<HandlerInterceptor> handlers = new ArrayList<>();

    private static Logger log = LoggerFactory.getLogger(MultipleHandlerInerceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        for (HandlerInterceptor item : handlers) {
            if (!item.preHandle(request, response, handler)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        for (int i = handlers.size() - 1; i >= 0; i--) {
            HandlerInterceptor item = handlers.get(i);
            try {
                item.postHandle(request, response, handler, modelAndView);
            } catch (Exception e) {
                log.error("", e);
            }
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        for (int i = handlers.size() - 1; i >= 0; i--) {
            HandlerInterceptor item = handlers.get(i);
            try {
                item.afterCompletion(request, response, handler, ex);
            } catch (Exception e) {
                log.error("", e);
            }
        }
    }

    public void setHandlers(List<HandlerInterceptor> handlers) {
        this.handlers = handlers;
    }
}
