package org.treeleaf.common.http.httpclient;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.treeleaf.common.http.HttpException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

/**
 * @author leaf
 * @date 2016-09-27 16:29
 */
public class HttpClientFactory {

    private static Logger log = LoggerFactory.getLogger(HttpClientFactory.class);

    private static HttpClient httpClient_https = null;

    static {
        SSLConnectionSocketFactory sslConnectionSocketFactory = buildSSLConnectionSocketFactory();

        RequestConfig config = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT).build();

        Registry<ConnectionSocketFactory> socketFactoryRegistry =
                RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("http", PlainConnectionSocketFactory.INSTANCE)
                        .register("https", sslConnectionSocketFactory).build();

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);

        httpClient_https = HttpClients.custom().setConnectionManager(connectionManager).setDefaultRequestConfig(config).build();
    }

    private static SSLConnectionSocketFactory buildSSLConnectionSocketFactory() {
        SSLContext ctx = null;
        try {
            ctx = SSLContext.getInstance("SSL");
            X509TrustManager tm = new X509TrustManager() {

                public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {

                }

                public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
        } catch (Exception e) {
            log.error("", e);

        }
        return new SSLConnectionSocketFactory(ctx);
    }

    public static HttpClient get() {
        return httpClient_https;
    }

    /**
     * get请求
     *
     * @param url   地址
     * @param param 参数
     * @return
     */
    public static String get(String url, Map param) {

        if (!param.isEmpty()) {
            String ps = org.treeleaf.common.http.basic.Http.param2UrlParam(param);
            url += "?" + ps;
        }

        HttpGet httpget = new HttpGet(url);
        ResponseHandler responseHandler = new BasicResponseHandler();
        try {
            return (String) httpClient_https.execute(httpget, responseHandler);
        } catch (IOException e) {
            throw new HttpException("调用" + url + "出错", e);
        }
    }
}
