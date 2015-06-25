package org.treeleaf.web.spring.view;

import org.springframework.web.servlet.view.AbstractView;
import org.treeleaf.common.json.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class FastJsonView extends AbstractView {

    public static final String CONTENT_TYPE = "application/json";

    private String charset = "UTF-8";

    private Object obj;

    public FastJsonView() {
        setContentType(CONTENT_TYPE);
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
        response.getWriter().println(json);
        response.getWriter().close();
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
