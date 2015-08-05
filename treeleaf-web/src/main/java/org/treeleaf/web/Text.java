package org.treeleaf.web;

/**
 * @Author leaf
 * 2015/7/19 0019 12:18.
 */
public class Text implements Result {

    /**
     * 返回html协议的网页
     */
    public final static String CONTENT_TYPE_HTML = "text/html";

    /**
     * 返回纯文本
     */
    public final static String CONTENT_TYPE_TEXT = "text/plain";

    /**
     * 字符集,默认UTF-8
     */
    private String charset;

    /**
     * HTTP协议内容类型
     */
    private String contentType;

    /**
     * 内容
     */
    private String content;

    public Text(String content) {
        this(CONTENT_TYPE_TEXT, content);
    }

    public Text(String contentType, String content) {
        this(contentType, content, "utf-8");
    }

    public Text(String contentType, String content, String charset) {
        this.contentType = contentType;
        this.content = content;
        this.charset = charset;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }
}