package org.treeleaf.web.spring.resovler;

import org.springframework.web.servlet.ModelAndView;
import org.treeleaf.web.spring.CommonConstant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 专门处理.html后缀的请求发生异常时的处理
 * <p/>
 * Created by yaoshuhong on 2015/5/30.
 */
public class HtmlExceptionExtHandler extends AbstractBaseExceptionExtHandler {


    /**
     * 当发生异常时默认的渲染视图
     */
    private String defaultErrorView = CommonConstant.DEFAULT_ERROR_VIEW_NAME;

    private String defaultErrorMsg;

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelAndView mav = new ModelAndView(defaultErrorView);
        return mav.addObject(CommonConstant.ATTR_EXCEPTION, ex);
    }


    public void setDefaultErrorMsg(String defaultErrorMsg) {
        this.defaultErrorMsg = defaultErrorMsg;
    }

    public void setDefaultErrorView(String defaultErrorView) {
        this.defaultErrorView = defaultErrorView;
    }
}
