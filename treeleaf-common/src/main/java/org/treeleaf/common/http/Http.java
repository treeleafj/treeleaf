package org.treeleaf.common.http;

import org.apache.commons.lang3.StringUtils;

import javax.net.ssl.*;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Http请求父类
 * <p>
 * Created by yaoshuhong on 2015/6/29.
 */
public class Http extends HttpConnectionAttr {

    public static final String NAME_CONTENT_TYPE = "Content-Type";

    public static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";

    public static final String CONTENT_TYPE_JSON = "application/json";

    public static final String CONTENT_TYPE_XML = "text/xml";

    private static final String CHARSET = "UTF-8";

    private SSLSocketFactory sslSocketFactory;

    private HostnameVerifier hostnameVerifier;

    /**
     * 根据是否采用ssl加密获取对应的HtppURLConnection实例
     *
     * @param url
     * @return
     * @throws Exception
     */
    protected HttpURLConnection getHttpURLConnection(URL url) throws Exception {
        if (this.isSsl()) {

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
     * @param param 参数
     * @return
     */
    public static String param2UrlParam(Map<String, String> param) {

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
                    stringBuilder.append(URLEncoder.encode(entry.getValue(), CHARSET));
                    stringBuilder.append(s2);
                }
            }

            if (stringBuilder.length() > 0) {
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            }

            return stringBuilder.toString();

        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(String.format("参数中%s存在非法字符串,无法使用%s转义.", param.toString(), CHARSET), e);
        }
    }

    public void setSslSocketFactory(SSLSocketFactory sslSocketFactory) {
        this.sslSocketFactory = sslSocketFactory;
    }

    public void setHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.hostnameVerifier = hostnameVerifier;
    }
}
