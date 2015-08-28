package org.treeleaf.web.spring.resovler;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import org.springframework.web.servlet.view.RedirectView;
import org.treeleaf.common.http.Http;
import org.treeleaf.web.Html;
import org.treeleaf.web.Redirect;
import org.treeleaf.web.Result;
import org.treeleaf.web.Text;
import org.treeleaf.web.spring.CommonConstant;
import org.treeleaf.web.spring.view.TextView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 异常处理
 * <p>
 * .json后缀的返回json格式的错误信息,其他的返回error页面
 * <p>
 * Created by yaoshuhong on 2015/5/5.
 */
public class ExHandlerExceptionResolver extends AbstractHandlerExceptionResolver {

    private static Logger log = LoggerFactory.getLogger(ExHandlerExceptionResolver.class);

    private int status = 200;

    private ExExceptionHanlder exExceptionHanlder;

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        //为了在BaseHandlerInterceptor中使用,将真正的异常缓存在request的attr中
        request.setAttribute(CommonConstant.ATTR_CACHE_EXCEPTION, ex);

        if (exExceptionHanlder != null) {

            String path = request.getServletPath();
            String ext = getUriExt(path);

            ErrorInfo errorInfo = new ErrorInfo();
            errorInfo.setException(ex);
            errorInfo.setUri(request.getRequestURI());
            errorInfo.setExt(ext);

            Result result = exExceptionHanlder.invoke(errorInfo);
            if (result != null) {
                response.setStatus(status);

                if (result instanceof Text) {//处理纯文字或json
                    return new ModelAndView(new TextView((Text) result));
                } else if (result instanceof Html) {//处理后台forward
                    Html html = (Html) result;
                    ModelAndView mav = new ModelAndView(html.getPath());
                    mav.addObject("model", html.getModel());
                    return mav;
                } else if (result instanceof Redirect) {//处理前台redirect
                    Redirect redirect = (Redirect) result;
                    String param = Http.param2UrlParam(redirect.getParam());
                    StringBuilder stringBuilder = new StringBuilder(redirect.getPath());
                    if (StringUtils.isNotBlank(param)) {
                        stringBuilder.append('?');
                        stringBuilder.append(param);
                    }
                    ModelAndView mav = new ModelAndView();
                    mav.setView(new RedirectView(stringBuilder.toString()));
                    return mav;
                }

                throw new RuntimeException("不支持的渲染类型:" + result.getClass().getName());
            }
        }
        return null;
    }

    private String getUriExt(String path) {
        String ext = "";
        int index = path.lastIndexOf(".");
        if (index > 0) {
            ext = path.substring(index);
        }
        return ext;
    }

    public void setExExceptionHanlder(ExExceptionHanlder exExceptionHanlder) {
        this.exExceptionHanlder = exExceptionHanlder;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
