package org.treeleaf.common.http;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Http Post请求
 * <p/>
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
     * @return
     * @throws org.treeleaf.common.http.HttpException
     */
    public String post() {

        String paramStr;
        if (StringUtils.isNotBlank(this.body)) {
            paramStr = HttpUtils.paramUrlEncode2String(this.getParam());
        } else {
            paramStr = this.body;
        }

        OutputStream out = null;
        InputStream in = null;
        HttpURLConnection conn = null;

        try {
            URL url = new URL(this.getAddress());
            // 打开和URL之间的连接
            conn = (HttpURLConnection) url.openConnection();

            conn.setConnectTimeout(this.getConnectTimeout());
            conn.setReadTimeout(this.getReadTimeout());

            // 设置的请求属性
            for (String name : this.getHeader()) {
                conn.setRequestProperty(name, this.getHeader().getHeader(name));
            }

            out = conn.getOutputStream();

            IOUtils.write(paramStr, out, this.getEncoding());

            in = conn.getInputStream();

            return IOUtils.toString(in, this.getEncoding());

        } catch (Exception e) {

            throw new HttpException("post方式请求远程地址失败", e);

        } finally {

            IOUtils.closeQuietly(out);
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
