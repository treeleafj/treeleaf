package org.treeleaf.web.spring.view;

import org.apache.commons.io.IOUtils;
import org.springframework.web.servlet.view.AbstractView;
import org.treeleaf.common.json.JsonUtils;
import org.treeleaf.web.spring.CommonConstant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 拓展springmvc的视图,基于FastJson提供的json解析功能
 * <p/>
 * Created by yaoshuhong on 2015/4/29.
 */
public class FastJsonView extends AbstractView {


    private String charset = CommonConstant.DEFAULT_CHARSET;

    private Object obj;

    public FastJsonView() {
        setContentType(CommonConstant.CONTENT_TYPE_JSON);
        setExposePathVariables(false);
    }

    public FastJsonView(Object obj) {
        this();
        this.obj = obj;
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model,
                                           HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setContentType("text/plain;charset=" + charset);
        response.setCharacterEncoding(charset);

        if (this.obj == null) {
            this.obj = model;
        }

        String json = JsonUtils.toJson(this.obj);
        IOUtils.write(json, response.getOutputStream(), charset);
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
