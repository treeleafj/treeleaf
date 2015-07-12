package org.treeleaf.common.http;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Http get请求
 * <p/>
 * Created by yaoshuhong on 2015/6/29.
 */
public class HttpGet extends Http {

    private static Logger log = LoggerFactory.getLogger(HttpGet.class);

    /**
     * 请求远程地址
     * @return
     * @throws org.treeleaf.common.http.HttpException
     */
    public String get() {

        String address = this.getAddress();

        //组装参数
        if (this.getParam() != null && !this.getParam().isEmpty()) {
            address += ("?" + HttpUtils.paramUrlEncode2String(this.getParam()));
        }

        HttpURLConnection conn = null;
        InputStream in = null;

        try {
            URL url = new URL(address);

            // 打开和URL之间的连接
            conn = (HttpURLConnection) url.openConnection();

            conn.setConnectTimeout(this.getConnectTimeout());
            conn.setReadTimeout(this.getReadTimeout());

            // 设置的请求属性
            for (String name : this.getHeader()) {
                conn.setRequestProperty(name, this.getHeader().getHeader(name));
            }

            conn.connect();// 建立实际的连接

            // 定义 BufferedReader 输入流来读取URL的响应
            in = conn.getInputStream();

            return IOUtils.toString(in, this.getEncoding());

        } catch (Exception e) {

            throw new HttpException("get方式请求远程地址失败", e);

        } finally {

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


}
