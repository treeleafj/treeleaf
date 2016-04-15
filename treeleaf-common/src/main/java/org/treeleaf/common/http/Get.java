package org.treeleaf.common.http;

import org.apache.commons.lang3.StringUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
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

    public Get hostnameVerifier(HostnameVerifier hostnameVerifier) {
        httpGet.setHostnameVerifier(hostnameVerifier);
        return this;
    }

    public Get sslSocketFactory(SSLSocketFactory sslSocketFactory) {
        httpGet.setSslSocketFactory(sslSocketFactory);
        return this;
    }

    public String get(boolean... retry) {
        return httpGet.get(retry);
    }

    public void get(OutputStream out, boolean... retry) {
        httpGet.get(out, retry);
    }

    public static void main(String[] args) {

        Map<String, String> param = new HashMap<>();
        param.put("a", "3");
        param.put("b", "3");

        String r = new Post("http://localhost:8081/products/list.json").params(param).post(true);
        System.out.println(r);
    }
}
