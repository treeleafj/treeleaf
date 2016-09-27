package org.treeleaf.common.http.httpclient;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
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
 * 封装HttpPost, 提供更精简的Api操作
 *
 * @author yaoshuhong
 * @date 2016-06-21 16:32
 */
public class Post extends Http<Post> {

    private static Logger log = LoggerFactory.getLogger(Post.class);

    /**
     * http post报文体中的参数数据
     */
    private String body;

    /**
     * 构建http请求对象
     *
     * @param address 请求地址,可以是http或者https
     */
    public Post(String address) {
        super(address);
    }

    public String send() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        this.send(out);
        try {
            return out.toString(this.encoding);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("将返回数据转移为" + this.encoding + "失败", e);
        }
    }

    public void send(OutputStream out) {
        HttpClient httpClient;
        try {
            httpClient = buildHttpClient();
        } catch (Exception e) {
            throw new HttpException("构建httpclient失败");
        }

        RequestBuilder post = RequestBuilder.post();
        if (StringUtils.isNotBlank(this.body)) {
            post.setEntity(new StringEntity(this.body, this.encoding));
        }

        HttpUriRequest httpUriRequest = this.buildHttpUriRequest(post);

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
                log.warn("Post异常响应{}，收到:{}", response.getStatusLine(), result);
            }
        } catch (IOException e) {
            throw new HttpException("post方式请求远程地址" + this.address + "失败", e);
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }
    }

    /**
     * 设置Post请求的Body
     *
     * @param body 请求体
     * @return Get对象
     */
    public Post body(String body) {
        this.body = body;
        return this;
    }
}
