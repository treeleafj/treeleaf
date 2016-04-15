package org.treeleaf.common.http;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

/**
 * Http Post请求
 * <p>
 * Created by yaoshuhong on 2015/6/29.
 */
public class HttpPost extends Http {

    private static Logger log = LoggerFactory.getLogger(HttpPost.class);

    /**
     * http post报文体中的参数数据
     */
    private String body;

    /**
     * 请求远程地址
     *
     * @return
     * @throws org.treeleaf.common.http.HttpException
     */
    public String post(boolean... retry) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        post(out, retry);
        try {
            return out.toString(getEncoding());
        } catch (UnsupportedEncodingException e) {
            throw new HttpException("将返回数据转移为" + getEncoding() + "失败", e);
        }
    }

    /**
     * 请求远程地址,将内容输出到指定的OutputStream输出流,试用于文件下载
     *
     * @param out
     */
    public void post(OutputStream out, boolean... retry) {

        String paramStr = "";

        if (StringUtils.isNotBlank(this.body)) {
            paramStr = this.body;
        } else if (this.getParam() != null) {
            paramStr = Http.param2UrlParam(this.getParam());
        }

        InputStream in = null;
        OutputStream out2 = null;
        HttpURLConnection conn = null;

        try {

            try {

                URL url = new URL(this.getAddress());
                // 打开和URL之间的连接
                conn = getHttpURLConnection(url);

                conn.setAllowUserInteraction(false);
                conn.setUseCaches(false);
                conn.setRequestMethod("POST");
                conn.setConnectTimeout(this.getConnectTimeout());
                conn.setReadTimeout(this.getReadTimeout());
                conn.setDoOutput(true);
                conn.setDoInput(true);

                // 设置的请求属性
                for (String name : this.getHeader()) {
                    conn.setRequestProperty(name, this.getHeader().getHeader(name));
                }

                out2 = conn.getOutputStream();

                IOUtils.write(paramStr, out2, this.getEncoding());

                in = conn.getInputStream();

                IOUtils.copy(in, out);
            } catch (SocketTimeoutException ste) {
                if (retry.length > 0 && retry[0]) {
                    log.warn("调用{}失败:{},进行重复尝试", this.getAddress(), ste.getMessage());

                    IOUtils.closeQuietly(in);
                    IOUtils.closeQuietly(out2);

                    URL url = new URL(this.getAddress());
                    // 打开和URL之间的连接
                    conn = getHttpURLConnection(url);

                    conn.setAllowUserInteraction(false);
                    conn.setUseCaches(false);
                    conn.setRequestMethod("POST");
                    conn.setConnectTimeout(this.getConnectTimeout());
                    conn.setReadTimeout(this.getReadTimeout());
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    // 设置的请求属性
                    for (String name : this.getHeader()) {
                        conn.setRequestProperty(name, this.getHeader().getHeader(name));
                    }

                    out2 = conn.getOutputStream();

                    IOUtils.write(paramStr, out2, this.getEncoding());

                    in = conn.getInputStream();

                    IOUtils.copy(in, out);
                } else {
                    throw ste;
                }
            }

        } catch (Exception e) {
            throw new HttpException("post方式请求远程地址失败", e);
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
