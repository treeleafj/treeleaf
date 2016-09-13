package org.treeleaf.common.http.basic;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.treeleaf.common.http.HttpException;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

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

        String paramStr = "";

        if (StringUtils.isNotBlank(this.body)) {
            paramStr = this.body;
        } else if (this.param != null) {
            paramStr = param2UrlParam(this.param);
        }

        InputStream in = null;
        OutputStream out2 = null;
        HttpURLConnection conn = null;

        try {

            try {

                URL url = new URL(this.address);
                // 打开和URL之间的连接
                conn = this.buildHttpURLConnection(url);

                conn.setAllowUserInteraction(false);
                conn.setUseCaches(false);
                conn.setRequestMethod("POST");
                conn.setConnectTimeout(this.connectTimeout);
                conn.setReadTimeout(this.readTimeout);
                conn.setDoOutput(true);
                conn.setDoInput(true);

                // 设置的请求属性
                for (String name : this.header) {
                    conn.setRequestProperty(name, this.header.getHeader(name));
                }

                out2 = conn.getOutputStream();

                IOUtils.write(paramStr, out2, this.encoding);

                in = conn.getInputStream();

                IOUtils.copy(in, out);
            } catch (SocketTimeoutException ste) {
                if (this.retry) {
                    log.warn("调用{}失败:{},进行重复尝试", this.address, ste.getMessage());

                    IOUtils.closeQuietly(in);
                    IOUtils.closeQuietly(out2);

                    URL url = new URL(this.address);
                    // 打开和URL之间的连接
                    conn = buildHttpURLConnection(url);

                    conn.setAllowUserInteraction(false);
                    conn.setUseCaches(false);
                    conn.setRequestMethod("POST");
                    conn.setConnectTimeout(this.connectTimeout);
                    conn.setReadTimeout(this.readTimeout);
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    // 设置的请求属性
                    for (String name : this.header) {
                        conn.setRequestProperty(name, this.header.getHeader(name));
                    }

                    out2 = conn.getOutputStream();

                    IOUtils.write(paramStr, out2, this.encoding);

                    in = conn.getInputStream();

                    IOUtils.copy(in, out);
                } else {
                    throw ste;
                }
            }

        } catch (Exception e) {
            throw new HttpException("post方式请求远程地址" + this.address + "失败", e);
        } finally {
            IOUtils.closeQuietly(out);
            IOUtils.closeQuietly(out2);
            IOUtils.closeQuietly(in);
            if (conn != null) {
                try {
                    conn.disconnect();
                } catch (Exception e) {
                    log.warn("关闭HttpURLConnection失败.", e);
                }
            }
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
