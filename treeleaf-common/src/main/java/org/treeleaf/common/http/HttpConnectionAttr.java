package org.treeleaf.common.http;

import java.util.Map;

/**
 * Http连接属性
 *
 * @Author leaf
 * 2015/8/21 0021 1:30.
 */
public abstract class HttpConnectionAttr {

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

    /**
     * 是否采用ssl(https)连接
     */
    private boolean ssl = false;

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

    public boolean isSsl() {
        return ssl;
    }

    public void setSsl(boolean ssl) {
        this.ssl = ssl;
    }

}
