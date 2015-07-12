package org.treeleaf.web.spring.resovler;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 后缀处理器,专门处理特定后缀名触发的事件<br/>
 * 比如HtmlExtHandler可以专门处理 xxxx.html请求发生的异常,<br/>
 * JsonExtHandler可以专门处理xxx.json请求发生的异常<br/>
 * 该类主要用于spring的统一异常处理那里处理不同请求后缀的异常
 * <p/>
 * Created by yaoshuhong on 2015/5/30.
 */
public interface ExceptionExtHandler {

    /**
     * 处理异常信息,并返回ModelAndView对象供spring mvc框架渲染
     * 需实现该方法实现真正的处理,参数参照 spring的AbstractHandlerExceptionResolver类
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @return
     * @see org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver
     */
    ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex);

}
