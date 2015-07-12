package org.treeleaf.common.http;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Http工具
 * <p/>
 * Created by yaoshuhong on 2015/4/24.
 */
public class HttpUtils {

    private static Logger log = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * 默认编码
     */
    public final static String ENCODEING = "UTF-8";

    /**
     * 读超时
     */
    public static final int READ_TIMEOUT = 60000;

    /**
     * 链接超时
     */
    public static final int CONNECTION_TIMEOUT = 2000;

    /**
     * 默认的Content-Type,设置为别的可能导致服务器收不到参数
     */
    public static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";

    public static final String CONTENT_TYPE_JSON = "application/json";

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param address 发送请求的URL
     * @param param   请求参数
     * @return URL 所代表远程资源的响应结果
     */
    public static String get(String address, Map<String, String> param) {


        //组装参数
        if (param != null && !param.isEmpty()) {
            address += ("?" + paramUrlEncode2String(param));
        }

        HttpURLConnection connection = null;
        InputStream in = null;

        try {
            URL url = new URL(address);
            // 打开和URL之间的连接
            connection = (HttpURLConnection) url.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

            connection.connect();// 建立实际的连接

            // 定义 BufferedReader 输入流来读取URL的响应
            in = connection.getInputStream();

            return IOUtils.toString(in, ENCODEING);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(in);
            closeQuietly(connection);
        }
    }

    /**
     * 向指定 URL 发送POST方法的请求, 以application/json的方式
     *
     * @param address 发送请求的 URL
     * @param param   请求参数
     * @return 所代表远程资源的响应结果
     */
    public static String post(String address, Map<String, String> param) {
        String paramStr = paramUrlEncode2String(param);
        return post2Body(address, paramStr, CONTENT_TYPE_JSON);
    }

    /**
     * 向指定 URL 发送POST方法的请求，以application/x-www-form-urlencoded的方式
     *
     * @param address 发送请求的 URL
     * @param param   请求参数
     * @return 所代表远程资源的响应结果
     */
    public static String postForm(String address, Map<String, String> param) {
        String paramStr = paramUrlEncode2String(param);
        return post2Body(address, paramStr, CONTENT_TYPE_FORM);
    }

    /**
     * 向指定 URL 发送POST方法的请求,同时将data数据放在body里面
     *
     * @param address 发送请求的 URL
     * @param data    请求参数
     * @return 所代表远程资源的响应结果
     */
    public static String post2Body(String address, String data, String contentType) {

        OutputStream out = null;
        InputStream in = null;
        HttpURLConnection conn = null;
        try {
            URL url = new URL(address);
            // 打开和URL之间的连接
            conn = (HttpURLConnection) url.openConnection();

            setPostProperty(conn, contentType);

            out = conn.getOutputStream();

            IOUtils.write(data, out, ENCODEING);

            in = conn.getInputStream();

            return IOUtils.toString(in, ENCODEING);

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(out);
            IOUtils.closeQuietly(in);
            closeQuietly(conn);
        }
    }

    /**
     * 下载文件
     *
     * @param address      文件地址
     * @param param        参数
     * @param outputStream 输出流
     * @return
     */
    public static void download(String address, Map param, OutputStream outputStream) {
        download(address, paramUrlEncode2String(param), outputStream);
    }

    /**
     * 下载文件
     *
     * @param address      文件地址
     * @param data         参数
     * @param outputStream 输出流
     * @return
     */
    public static void download(String address, String data, OutputStream outputStream) {
        OutputStream out = null;
        InputStream in = null;
        HttpURLConnection conn = null;
        try {
            URL realUrl = new URL(address);
            // 打开和URL之间的连接
            conn = (HttpURLConnection) realUrl.openConnection();

            setPostProperty(conn, CONTENT_TYPE_FORM);

            out = conn.getOutputStream();

            IOUtils.write(data, out, ENCODEING);

            in = conn.getInputStream();

            IOUtils.copy(in, outputStream);

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(out);
            IOUtils.closeQuietly(in);
            closeQuietly(conn);
        }
    }

    public static HttpURLConnection connect(String address, String data) {
        OutputStream out = null;
        HttpURLConnection conn;

        try {
            URL realUrl = new URL(address);

            // 打开和URL之间的连接
            conn = (HttpURLConnection) realUrl.openConnection();

            setPostProperty(conn, CONTENT_TYPE_FORM);

            out = conn.getOutputStream();

            IOUtils.write(data, out, ENCODEING);

            return conn;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(out);
        }
    }

    /**
     * 安静的关闭HttpURLConnection
     *
     * @param connection
     */
    private static void closeQuietly(HttpURLConnection connection) {
        if (connection != null) {
            try {
                connection.disconnect();
            } catch (Exception e) {
                log.warn("关闭HttpURLConnection失败.", e);
            }
        }
    }

    /**
     * 设置HttpURLConnection的属性为POST
     *
     * @param conn
     * @param contentType
     * @throws java.net.ProtocolException
     */
    private static void setPostProperty(HttpURLConnection conn, String contentType) throws ProtocolException {
        conn.setAllowUserInteraction(false);
        conn.setUseCaches(false);
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(CONNECTION_TIMEOUT);
        conn.setReadTimeout(READ_TIMEOUT);
        conn.setRequestProperty("Content-Type", contentType);

        // 发送POST请求必须设置如下两行
        conn.setDoOutput(true);
        conn.setDoInput(true);
    }

    /**
     * 将map参数使用'&'符号链接起来
     *
     * @param param 参数
     * @return
     */
    public static String paramUrlEncode2String(Map<String, String> param) {

        if (param == null || param.isEmpty()) {
            return StringUtils.EMPTY;
        }

        StringBuilder stringBuilder = new StringBuilder();
        try {
            String s1 = "=";
            String s2 = "&";

            for (Map.Entry<String, String> entry : param.entrySet()) {
                if (entry.getValue() != null) {
                    stringBuilder.append(entry.getKey());
                    stringBuilder.append(s1);
                    stringBuilder.append(URLEncoder.encode(entry.getValue(), ENCODEING));
                    stringBuilder.append(s2);
                }
            }

            if (stringBuilder.length() > 0) {
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            }

            return stringBuilder.toString();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(String.format("参数中%s存在非法字符串,无法使用%s转义.", param.toString(), ENCODEING), e);
        }
    }
}
