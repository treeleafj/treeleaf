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
public class Post {

    private HttpPost httpPost = new HttpPost();

    public Post(String addr) {
        boolean isSsl = StringUtils.startsWithIgnoreCase(addr, "https");
        httpPost.setSsl(isSsl);
        httpPost.setAddress(addr);
    }

    public Post params(Map<String, String> param) {
        if (httpPost.getParam() == null || httpPost.getParam().isEmpty()) {
            httpPost.setParam(param);
        } else {
            httpPost.getParam().putAll(param);
        }
        return this;
    }

    public Post param(String name, String value) {
        if (httpPost.getParam() == null) {
            httpPost.setParam(new HashMap<>());
        }
        httpPost.getParam().put(name, value);
        return this;
    }

    public Post body(String body) {
        httpPost.setBody(body);
        return this;
    }

    public Post connectTimeout(int t) {
        httpPost.setConnectTimeout(t);
        return this;
    }

    public Post readTimeout(int t) {
        httpPost.setReadTimeout(t);
        return this;
    }

    public Post encoding(String encoding) {
        httpPost.setEncoding(encoding);
        return this;
    }

    public Post header(HttpHeader header) {
        httpPost.setHeader(header);
        return this;
    }

    public Post header(String name, String val) {
        httpPost.addHeader(name, val);
        return this;
    }

    public Post hostnameVerifier(HostnameVerifier hostnameVerifier) {
        httpPost.setHostnameVerifier(hostnameVerifier);
        return this;
    }

    public Post sslSocketFactory(SSLSocketFactory sslSocketFactory) {
        httpPost.setSslSocketFactory(sslSocketFactory);
        return this;
    }

    public String post(boolean... retry) {
        return httpPost.post(retry);
    }

    public void post(OutputStream out, boolean... retry) {
        httpPost.post(out, retry);
    }
}
