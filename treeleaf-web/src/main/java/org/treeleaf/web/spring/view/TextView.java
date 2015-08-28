package org.treeleaf.web.spring.view;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.AbstractView;
import org.treeleaf.web.Text;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author leaf
 * 2015/7/19 0019 12:26.
 */
public class TextView extends AbstractView {

    private static Logger log = LoggerFactory.getLogger(TextView.class);

    private Text text;

    public TextView(Text text) {
        this.text = text;
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(text.getContentType() + ";charset=" + this.text.getCharset());
        response.setCharacterEncoding(this.text.getCharset());
        String content = this.text.getContent();
        IOUtils.write(content, response.getOutputStream(), this.text.getCharset());
    }
}
