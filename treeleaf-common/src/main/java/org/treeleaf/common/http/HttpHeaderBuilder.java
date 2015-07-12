package org.treeleaf.common.http;

/**
 * Created by yaoshuhong on 2015/6/29.
 */
public class HttpHeaderBuilder {

    /**
     * 构建默认的HttpHeader对象
     * @return
     */
    public static HttpHeader buildDefaultHttpHeader() {
        HttpHeader httpHeader = new HttpHeader();
        httpHeader.addHeader("accept", "*/*");
        httpHeader.addHeader("connection", "Keep-Alive");
        httpHeader.addHeader("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        return httpHeader;
    }

}
