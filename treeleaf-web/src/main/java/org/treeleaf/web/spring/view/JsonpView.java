package org.treeleaf.web.spring.view;

import org.apache.commons.io.IOUtils;
import org.treeleaf.web.Text;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author leaf
 * 2016/3/5 0005 15:29.
 */
public class JsonpView extends TextView {

    private Text text;

    public JsonpView(Text text) {
        super(text);
        this.text = text;
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(text.getContentType() + ";charset=" + this.text.getCharset());
        response.setCharacterEncoding(this.text.getCharset());

        String method = request.getParameter("jsonpcallback");
        String content = this.text.getContent();
        StringBuilder sb = new StringBuilder();
        sb.append(method);
        sb.append('(');
        sb.append(content);
        sb.append(");");
        IOUtils.write(sb.toString(), response.getOutputStream(), this.text.getCharset());
    }
}
