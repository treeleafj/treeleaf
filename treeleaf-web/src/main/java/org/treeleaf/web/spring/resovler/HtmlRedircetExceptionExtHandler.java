package org.treeleaf.web.spring.resovler;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.treeleaf.common.exception.BaseException;
import org.treeleaf.common.http.HttpUtils;
import org.treeleaf.web.spring.CommonConstant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author leaf
 * 2015/7/20 0020 0:13.
 */
public class HtmlRedircetExceptionExtHandler  extends AbstractBaseExceptionExtHandler {


    /**
     * 当发生异常时默认的渲染视图
     */
    private String defaultErrorView = CommonConstant.DEFAULT_ERROR_VIEW_NAME;

    private String defaultErrorMsg;

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        Map<String, String> paramMap = new HashMap<String, String>();

        if (ex instanceof BaseException) {
            paramMap.put("msg", ex.getMessage());
        } else {
            paramMap.put("msg", defaultErrorMsg);
        }


        String param = HttpUtils.paramUrlEncode2String(paramMap);
        StringBuilder stringBuilder = new StringBuilder(defaultErrorView);
        if (StringUtils.isNotBlank(param)) {
            stringBuilder.append('?');
            stringBuilder.append(param);
        }
        ModelAndView mav = new ModelAndView();
        mav.setView(new RedirectView(stringBuilder.toString()));
        return mav;
    }


    public void setDefaultErrorMsg(String defaultErrorMsg) {
        this.defaultErrorMsg = defaultErrorMsg;
    }

    public void setDefaultErrorView(String defaultErrorView) {
        this.defaultErrorView = defaultErrorView;
    }
}
