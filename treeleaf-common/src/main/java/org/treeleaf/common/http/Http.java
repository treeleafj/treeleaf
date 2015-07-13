package org.treeleaf.common.http;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
/**
 * Http请求父类
 *
 * Created by yaoshuhong on 2015/6/29.
 */
public abstract class Http {

    public static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";

    public static final String CONTENT_TYPE_JSON = "application/json";

    public static final String NAME_CONTENT_TYPE = "Content-Type";

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
     * 将map参数使用'&'符号链接起来
     *
     * @param param 参数
     * @return
     */
    public static String param2String(Map<String, String> param) {

        if (param == null || param.isEmpty()) {
            return StringUtils.EMPTY;
        }

        StringBuilder stringBuilder = new StringBuilder();
        try {
            String s1 = "=";
            String s2 = "&";

            for (Map.Entry<String, String> entry : param.entrySet()) {
                if (entry.getValue() != null) {
                    stringBuilder.append(entry.getKey());
                    stringBuilder.append(s1);
                    stringBuilder.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                    stringBuilder.append(s2);
                }
            }

            if (stringBuilder.length() > 0) {
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            }
            return stringBuilder.toString();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(String.format("参数中%s存在非法字符串,无法使用%s转义.", param.toString(), "UTF-8"), e);
        }
    }

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