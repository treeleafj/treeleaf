package org.treeleaf.common.http;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Http头部构建工具
 *
 * @author yaoshuhong
 * @date 2016-06-21 15:30
 */
public class HttpHeader implements Iterable<String> {

    public static final String NAME_CONTENT_TYPE = "Content-Type";

    public static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";

    public static final String CONTENT_TYPE_JSON = "application/json";

    public static final String CONTENT_TYPE_XML = "text/xml";

    private Map<String, String> header = new HashMap<>();

    /**
     * 构建默认的HttpHeader对象
     *
     * @return HttpHeader对象
     */
    public static HttpHeader defaultHttpHeader() {
        HttpHeader httpHeader = new HttpHeader();

        httpHeader.addHeader("Accept", "*/*");
//        httpHeader.addHeader("Connection", "Keep-Alive");
        httpHeader.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        httpHeader.addHeader("Content-Type", "application/x-www-form-urlencoded");
        return httpHeader;
    }

    public void addHeader(String name, String val) {
        this.header.put(name, val);
    }

    public String getHeader(String name) {
        return this.header.get(name);
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> head) {
        this.header = head;
    }

    @Override
    public Iterator<String> iterator() {
        return header.keySet().iterator();
    }

}
