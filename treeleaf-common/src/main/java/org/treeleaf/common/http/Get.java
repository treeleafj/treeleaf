package org.treeleaf.common.http;

import org.apache.commons.io.IOUtils;
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

        String address = this.address;

        //组装参数
        if (this.param != null && !this.param.isEmpty()) {
            address += ("?" + Http.param2UrlParam(this.param));
        }

        HttpURLConnection conn = null;
        InputStream in = null;

        try {

            try {
                URL url = new URL(address);

                // 打开和URL之间的连接
                conn = this.buildHttpURLConnection(url);

                conn.setConnectTimeout(this.connectTimeout);
                conn.setReadTimeout(this.readTimeout);
                conn.setRequestMethod("GET");

                // 设置的请求属性
                for (String name : this.header) {
                    conn.setRequestProperty(name, this.header.getHeader(name));
                }

                conn.connect();// 建立实际的连接

                //读取URL的响应
                in = conn.getInputStream();

                IOUtils.copy(in, out);
            } catch (SocketTimeoutException ste) {
                if (this.retry) {
                    log.warn("调用{}失败:{},进行重复尝试", address, ste.getMessage());

                    IOUtils.closeQuietly(in);

                    URL url = new URL(address);

                    // 打开和URL之间的连接
                    conn = this.buildHttpURLConnection(url);

                    conn.setConnectTimeout(this.connectTimeout);
                    conn.setReadTimeout(this.readTimeout);
                    conn.setRequestMethod("GET");

                    // 设置的请求属性
                    for (String name : this.header) {
                        conn.setRequestProperty(name, this.header.getHeader(name));
                    }

                    conn.connect();// 建立实际的连接

                    //读取URL的响应
                    in = conn.getInputStream();

                    IOUtils.copy(in, out);
                } else {
                    throw ste;
                }
            }

        } catch (Exception e) {
            throw new HttpException("get方式请求远程地址" + address + "失败", e);
        } finally {

            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);

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
