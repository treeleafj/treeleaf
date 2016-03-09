package org.treeleaf.common.http;

import org.apache.commons.lang3.StringUtils;

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

    public String post() {
        return httpPost.post();
    }

    public void post(OutputStream out) {
        httpPost.post(out);
    }

    public static void main(String[] args) {

        Map<String, String> param = new HashMap<>();
        param.put("a", "3");
        param.put("b", "3");

        String r = new Post("https://www.baidu.com").params(param).post();
        System.out.println(r);
    }
}
