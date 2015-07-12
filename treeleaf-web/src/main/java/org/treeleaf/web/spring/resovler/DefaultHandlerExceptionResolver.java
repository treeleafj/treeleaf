package org.treeleaf.web.spring.resovler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import org.treeleaf.web.spring.CommonConstant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 默认异常处理
 * <p/>
 * .json后缀的返回json格式的错误信息,其他的返回error页面
 * <p/>
 * Created by yaoshuhong on 2015/5/5.
 */
public class DefaultHandlerExceptionResolver extends AbstractHandlerExceptionResolver {

    private static Logger log = LoggerFactory.getLogger(DefaultHandlerExceptionResolver.class);


    /**
     * 当发生异常时默认的返回状态码
     */
    private int defaultErrorStatusCode = CommonConstant.DEFAULT_ERROR_STATUS_CODE;

    private Map<String, ExceptionExtHandler> extHandlers = new HashMap<String, ExceptionExtHandler>();

    private ExceptionExtHandler defaultExceptionExtHandler = null;

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request,
                                              HttpServletResponse response, Object handler, Exception ex) {

        //为了在BaseHandlerInterceptor中使用,将真正的异常缓存在request的attr中
        request.setAttribute(CommonConstant.ATTR_CACHE_EXCEPTION, ex);

        //设置默认的返回状态码
        response.setStatus(defaultErrorStatusCode);

        String path = request.getServletPath();
        String ext = getUriExt(path);

        ExceptionExtHandler exceptionExtHandler = this.extHandlers.get(ext);

        if (exceptionExtHandler == null && defaultExceptionExtHandler != null) {
            exceptionExtHandler = defaultExceptionExtHandler;
        }

        return exceptionExtHandler.handle(request, response, handler, ex);

    }

    private String getUriExt(String path) {
        String ext = "";
        int index = path.lastIndexOf(".");
        if (index > 0) {
            ext = path.substring(index);
        }
        return ext;
    }

    public void setExtHandlers(Map<String, ExceptionExtHandler> extHandlers) {
        this.extHandlers = extHandlers;
    }

    public void setDefaultErrorStatusCode(int defaultErrorStatusCode) {
        this.defaultErrorStatusCode = defaultErrorStatusCode;
    }

    public void setDefaultExceptionExtHandler(ExceptionExtHandler defaultExceptionExtHandler) {
        this.defaultExceptionExtHandler = defaultExceptionExtHandler;
    }
}
