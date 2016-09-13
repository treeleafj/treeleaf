package org.treeleaf.common.http.basic;

import org.apache.commons.lang3.StringUtils;
import org.treeleaf.common.http.HttpHeader;

import javax.net.ssl.*;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

/**
 * Http调用抽象父类, 提供Http请求基本属性设置
 *
 * @author yaoshuhong
 * @date 2016-06-21 16:18
 */
public abstract class Http<T extends Http> {

    /**
     * 链接超时, 10秒钟
     */
    protected int connectTimeout = 10000;

    /**
     * 读超时,60秒
     */
    protected int readTimeout = 60000;

    /**
     * 请求编码
     */
    protected String encoding = "UTF-8";

    /**
     * 访问地址
     */
    protected String address;

    /**
     * 请求头部
     */
    protected HttpHeader header = HttpHeader.defaultHttpHeader();

    /**
     * 请求参数
     */
    protected Map<String, String> param;

    /**
     * 是否采用ssl(https)连接
     */
    protected boolean ssl = false;

    /**
     * 是否重新尝试, 当连接超时时可以通过该属性决定是否重新发起申请
     */
    protected boolean retry = false;

    /**
     * 安全连接工厂,带有证书的https连接需要自己实现特定的SSLScoketFactory
     */
    protected SSLSocketFactory sslSocketFactory;

    /**
     * 主机名检验器,带有证书的https连接需要自己实现特定的HostnameVerifier
     */
    protected HostnameVerifier hostnameVerifier;

    /**
     * 构建http请求对象
     *
     * @param address 请求地址,可以是http或者https
     */
    public Http(String address) {
        this.address = address;
        this.ssl = StringUtils.startsWithIgnoreCase(address, "https");
    }

    /**
     * 发送请求
     *
     * @return 返回结果
     */
    public abstract String send();

    /**
     * 发送请求
     *
     * @param out 返回结果的输出流,适用于文件类下载
     */
    public abstract void send(OutputStream out);

    /**
     * 根据是否采用ssl加密获取对应的HtppURLConnection实例
     *
     * @param url 调用资源地址
     * @return
     * @throws Exception
     */
    protected HttpURLConnection buildHttpURLConnection(URL url) throws Exception {
        if (this.ssl) {

            if (sslSocketFactory == null) {
                SSLContext sc = SSLContext.getInstance("TLSv1.2");
                sc.init(null, new TrustManager[]{new TrustAnyTrustManager()},
                        new java.security.SecureRandom());
                this.sslSocketFactory = sc.getSocketFactory();
            }

            if (this.hostnameVerifier == null) {
                this.hostnameVerifier = new TrustAnyHostnameVerifier();
            }

            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(this.sslSocketFactory);
            conn.setHostnameVerifier(this.hostnameVerifier);
            return conn;
        }
        return (HttpURLConnection) url.openConnection();
    }

    /**
     * 将map参数使用'&'符号链接起来
     *
     * @param param    参数
     * @param encoding urlencode的编码,默认是utf-8
     * @return
     */
    public static String param2UrlParam(Map<String, String> param, String... encoding) {

        if (param == null || param.isEmpty()) {
            return StringUtils.EMPTY;
        }

        String enc = encoding.length > 0 ? encoding[0] : "utf-8";

        StringBuilder stringBuilder = new StringBuilder();
        try {
            String s1 = "=";
            String s2 = "&";

            for (Map.Entry<String, String> entry : param.entrySet()) {
                if (entry.getValue() != null) {
                    stringBuilder.append(entry.getKey());
                    stringBuilder.append(s1);
                    stringBuilder.append(URLEncoder.encode(entry.getValue(), enc));
                    stringBuilder.append(s2);
                }
            }

            if (stringBuilder.length() > 0) {
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            }

            return stringBuilder.toString();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(String.format("参数中%s存在非法字符串,无法使用%s转义.", param.toString(), enc), e);
        }
    }

    /**
     * 设置请求的参数
     *
     * @param param 请求参数,多次调用该方法,可以将参数累加起来,但key一样的会覆盖
     * @return Http请求对象
     */
    public T params(Map<String, String> param) {
        if (this.param == null || this.param.isEmpty()) {
            this.param = param;
        } else {
            this.param.putAll(param);
        }
        return (T) this;
    }

    /**
     * 往请求中追加一个参数
     *
     * @param name  参数名
     * @param value 参数值
     * @return Http请求对象
     */
    public T param(String name, String value) {
        if (this.param == null) {
            this.param = new HashMap<>();
        }
        this.param.put(name, value);
        return (T) this;
    }

    /**
     * 设置Http请求的连接超时时间
     *
     * @param t 连接超时时间,单位:毫秒
     * @return Http请求对象
     */
    public T connectTimeout(int t) {
        this.connectTimeout = t;
        return (T) this;
    }

    /**
     * 设置Http请求的读超时时间
     *
     * @param t 读超时时间,单位:毫秒
     * @return Http请求对象
     */
    public T readTimeout(int t) {
        this.readTimeout = t;
        return (T) this;
    }

    /**
     * 设置请求的编码
     *
     * @param encoding 编码,不设置的话默认是utf-8
     * @return Http请求对象
     */
    public T encoding(String encoding) {
        this.encoding = encoding;
        return (T) this;
    }

    /**
     * 设置http请求的头部
     *
     * @param header 请求头对象,不设置的话默认是HttpHeader.defaultHeader()
     * @return Http请求对象
     */
    public T header(HttpHeader header) {
        this.header = header;
        return (T) this;
    }

    /**
     * 往现有的http请求头部中增加一个头部参数,当key一样时会覆盖
     *
     * @param name 头部名称
     * @param val  头部值
     * @return Http请求对象
     */
    public T header(String name, String val) {
        this.header.addHeader(name, val);
        return (T) this;
    }

    /**
     * 设置主机名检验器,带有证书的https连接需要自己实现特定的HostnameVerifier
     *
     * @param hostnameVerifier 主机名检验器
     * @return Http请求对象
     */
    public T hostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.hostnameVerifier = hostnameVerifier;
        return (T) this;
    }

    /**
     * 设置安全连接工厂,带有证书的https连接需要自己实现特定的SSLScoketFactory
     *
     * @param sslSocketFactory 安全连接工厂
     * @return Http请求对象
     */
    public T sslSocketFactory(SSLSocketFactory sslSocketFactory) {
        this.sslSocketFactory = sslSocketFactory;
        return (T) this;
    }

    /**
     * 设置请求连接超时进行重新尝试
     *
     * @return Http请求对象
     */
    public T retry() {
        this.retry = true;
        return (T) this;
    }

    /**
     * 域名验器,默认都通过
     */
    public static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    /**
     * 证书算法管理器
     */
    public static class TrustAnyTrustManager implements X509TrustManager {

        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }

}
