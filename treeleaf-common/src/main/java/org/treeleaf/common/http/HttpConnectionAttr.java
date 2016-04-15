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

    /**
     * 是否重新尝试
     */
    private boolean retry;

    public void addHeader(String name, String value) {
        this.header.addHeader(name, value);
    }

    public String getHeader(String name) {
        return this.getHeader().getHeader(name);
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public HttpConnectionAttr setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public HttpConnectionAttr setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    public String getEncoding() {
        return encoding;
    }

    public HttpConnectionAttr setEncoding(String encoding) {
        this.encoding = encoding;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public HttpConnectionAttr setAddress(String address) {
        this.address = address;
        return this;
    }

    public HttpHeader getHeader() {
        return header;
    }

    public HttpConnectionAttr setHeader(HttpHeader header) {
        this.header = header;
        return this;
    }

    public Map<String, String> getParam() {
        return param;
    }

    public HttpConnectionAttr setParam(Map<String, String> param) {
        this.param = param;
        return this;
    }

    public boolean isSsl() {
        return ssl;
    }

    public HttpConnectionAttr setSsl(boolean ssl) {
        this.ssl = ssl;
        return this;
    }

    public boolean isRetry() {
        return retry;
    }

    public HttpConnectionAttr setRetry(boolean retry) {
        this.retry = retry;
        return this;
    }
}
