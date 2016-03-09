package org.treeleaf.common.http;

import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yaoshuhong on 2016/3/4.
 */
public class Get {

    private HttpGet httpGet = new HttpGet();

    public Get(String addr) {
        boolean isSsl = StringUtils.startsWithIgnoreCase(addr, "https");
        httpGet.setSsl(isSsl);
        httpGet.setAddress(addr);
    }

    public Get params(Map<String, String> param) {
        if (httpGet.getParam() == null || httpGet.getParam().isEmpty()) {
            httpGet.setParam(param);
        } else {
            httpGet.getParam().putAll(param);
        }
        return this;
    }

    public Get param(String name, String value) {
        if (httpGet.getParam() == null) {
            httpGet.setParam(new HashMap<>());
        }
        httpGet.getParam().put(name, value);
        return this;
    }

    public Get connectTimeout(int t) {
        httpGet.setConnectTimeout(t);
        return this;
    }

    public Get readTimeout(int t) {
        httpGet.setReadTimeout(t);
        return this;
    }

    public Get encoding(String encoding) {
        httpGet.setEncoding(encoding);
        return this;
    }

    public Get header(HttpHeader header) {
        httpGet.setHeader(header);
        return this;
    }

    public Get header(String name, String val) {
        httpGet.addHeader(name, val);
        return this;
    }

    public String get() {
        return httpGet.get();
    }

    public void get(OutputStream out) {
        httpGet.get(out);
    }

    public static void main(String[] args) {

        Map<String, String> param = new HashMap<>();
        param.put("a", "3");
        param.put("b", "3");

        String r = new Get("https://www.baidu.com").params(param).get();
        System.out.println(r);
    }
}
