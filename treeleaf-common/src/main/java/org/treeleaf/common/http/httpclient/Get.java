package org.treeleaf.common.http.httpclient;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.treeleaf.common.http.HttpException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * 封装HttpGet, 提供更精简的Api操作
 *
 * @author yaoshuhong
 * @date 2016-06-21 16:24
 */
public class Get extends Http<Get> {

    private static Logger log = LoggerFactory.getLogger(Get.class);

    /**
     * 构建Get请求对象
     *
     * @param address 请求地址,可以是http或者https
     */
    public Get(String address) {
        super(address);
    }

    public String send() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        this.send(out);
        try {
            return out.toString(this.encoding);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("将返回数据转移为" + encoding + "失败", e);
        }
    }

    public void send(OutputStream out) {
        HttpClient httpClient;
        try {
            httpClient = buildHttpClient();
        } catch (Exception e) {
            throw new HttpException("构建httpclient失败", e);
        }

        HttpUriRequest httpUriRequest = this.buildHttpUriRequest(RequestBuilder.get());

        HttpResponse response;
        InputStream in = null;
        try {
            response = httpClient.execute(httpUriRequest);

            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                in = entity.getContent();
                IOUtils.copy(in, out);
            } else {
                String result = EntityUtils.toString(response.getEntity(), this.encoding);
                log.warn("Get异常响应{}，收到:{}", response.getStatusLine(), result);
            }
        } catch (IOException e) {
            throw new HttpException("get方式请求远程地址" + address + "失败", e);
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }
    }
}
