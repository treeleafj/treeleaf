package org.treeleaf.common.http;

import java.util.Map;

/**
 * Http请求父类
 *
 * Created by yaoshuhong on 2015/6/29.
 */
public abstract class Http {

    /**
     * 链接超时
     */
    private int connectTimeout = 10000;

    /**
     * 读超时
     */
    private int readTimeout = 60000;

    /**
     * 请求编码
     */
    private String encoding = "UTF-8";

    /**
     * 访问地址
     */
    private String address;

    /**
     * 请求头部
     */
    private HttpHeader header = HttpHeaderBuilder.buildDefaultHttpHeader();

    /**
     * 请求参数
     */
    private Map<String, String> param;

    public void addHeader(String name, String value) {
        this.header.addHeader(name, value);
    }

    public String getHeader(String name) {
        return this.getHeader().getHeader(name);
    }

    public void setHeader(HttpHeader header) {
        this.header = header;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public HttpHeader getHeader() {
        return header;
    }

    public Map<String, String> getParam() {
        return param;
    }

    public void setParam(Map<String, String> param) {
        this.param = param;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }
}
