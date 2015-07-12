package org.treeleaf.web.spring.resovler;

import org.springframework.web.servlet.ModelAndView;
import org.treeleaf.common.safe.Assert;
import org.treeleaf.web.spring.view.FastJsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 专门处理.json后缀的请求发生异常时的处理
 * <p/>
 * Created by yaoshuhong on 2015/5/30.
 */
public class JsonExceptionExtHandler extends AbstractBaseExceptionExtHandler {

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        Object obj = null;

        for (Converter converter : this.getExceptionConverters()) {
            if (converter.support(ex)) {
                obj = converter.convert(ex);
                break;
            }
        }

        Assert.notNull(obj, "异常处理结果不能为空");

        return new ModelAndView(new FastJsonView(obj));
    }
}
