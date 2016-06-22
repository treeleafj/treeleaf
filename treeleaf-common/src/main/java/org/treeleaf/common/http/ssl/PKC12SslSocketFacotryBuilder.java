package org.treeleaf.common.http.ssl;

import org.treeleaf.common.safe.Assert;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import java.io.FileInputStream;
import java.security.KeyStore;

/**
 * Created by yaoshuhong on 2016/3/18.
 */
public class PKC12SslSocketFacotryBuilder {

    private String certPath;

    private String password;

    public PKC12SslSocketFacotryBuilder(String certPath, String password) {
        this.certPath = certPath;
        this.password = password;
    }

    public SSLSocketFactory build() {
        Assert.hasText(certPath, "证书路径不能为空");
        Assert.hasText(password, "证书密码不能为空");
        try {
            KeyStore ks = KeyStore.getInstance("PKCS12");
            ks.load(new FileInputStream(certPath), password.toCharArray());
            KeyManagerFactory tmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            tmf.init(ks, password.toCharArray());

            KeyManager[] keymanagers = tmf.getKeyManagers();
            SSLContext sslcontext = SSLContext.getInstance("TLS");

            sslcontext.init(keymanagers, null, null);

            return sslcontext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException("构建SslScoketFacotry失败", e);
        }
    }

}
