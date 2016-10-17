package org.treeleaf.common.http.httpclient;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.treeleaf.common.http.HttpHeader;

import java.io.OutputStream;
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
     * 读超时,15秒
     */
    protected int readTimeout = 15000;

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
    protected HttpHeader header;

    /**
     * 请求参数
     */
    protected Map<String, String> param;

    /**
     * 是否采用ssl(https)连接
     */
    protected boolean ssl = false;

//    /**
//     * 是否重新尝试, 当连接超时时可以通过该属性决定是否重新发起申请
//     */
//    protected boolean retry = false;

    /**
     * 安全连接工厂,带有证书的https连接需要自己实现特定的SSLScoketFactory
     */
    protected SSLConnectionSocketFactory sslSocketFactory;

    private static PoolingHttpClientConnectionManager connectionManager;

    static {
        connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(1000);
        connectionManager.setDefaultMaxPerRoute(100);//连接池最大连接数
    }

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

    protected HttpClient buildHttpClient() throws Exception {
        return HttpClientFactory.get();
    }

    protected HttpUriRequest buildHttpUriRequest(RequestBuilder builder) {
        RequestBuilder requestBuilder = builder.setUri(this.address);

        if (this.param != null && !this.param.isEmpty()) {
            for (Map.Entry<String, String> entry : this.param.entrySet()) {
                requestBuilder.addParameter(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }

        if (this.header != null) {
            for (String name : this.header) {
                requestBuilder.addHeader(name, this.header.getHeader(name));
            }
        }

        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(30000)
                .setConnectTimeout(this.connectTimeout)
                .setConnectionRequestTimeout(this.readTimeout)
                .build();

        requestBuilder.setConfig(requestConfig);

        return requestBuilder.build();
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
     * 设置安全连接工厂,带有证书的https连接需要自己实现特定的SSLScoketFactory
     *
     * @param sslSocketFactory 安全连接工厂
     * @return Http请求对象
     */
    public T sslSocketFactory(SSLConnectionSocketFactory sslSocketFactory) {
        this.sslSocketFactory = sslSocketFactory;
        return (T) this;
    }
}
