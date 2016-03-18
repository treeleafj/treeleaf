package org.treeleaf.wechat.pay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.treeleaf.common.bean.FastBeanUtils;
import org.treeleaf.common.http.BrowserCompatHostnameVerifier;
import org.treeleaf.common.http.Http;
import org.treeleaf.common.http.PKC12SslSocketFacotryBuilder;
import org.treeleaf.common.http.Post;
import org.treeleaf.common.safe.Uuid;
import org.treeleaf.wechat.pay.entity.GroupRedpack;
import org.treeleaf.wechat.pay.entity.Redpack;
import org.treeleaf.wechat.pay.entity.RedpackResult;

import javax.net.ssl.SSLSocketFactory;
import java.util.Map;

/**
 * 微信红包接口
 * <p>
 * Created by leaf on 2016/3/17 0017.
 */
public class WechatRedpack extends WechatMerchantInterface {

    private static Logger log = LoggerFactory.getLogger(WechatRedpack.class);

    private transient static SSLSocketFactory sslSocketFactory = null;

    /**
     * 应用id
     */
    private String appid;

    /**
     * 微信支付商户号
     */
    private String merchantNo;

    /**
     * 商户密钥
     */
    private String key;

    /**
     * CA证书位置
     */
    private String certPath;

    /**
     * 发送微信定额红包
     *
     * @param redpack
     */
    public RedpackResult send(Redpack redpack) {
        redpack.setWxappid(this.appid);
        redpack.setMch_id(this.merchantNo);
        redpack.setNonce_str(Uuid.buildBase64UUID());

        String sign = WechatPaySignature.sign(redpack, this.key);
        redpack.setSign(sign);

        Map<String, String> req;
        try {
            req = FastBeanUtils.describe(redpack);
            req.remove("class");
        } catch (Exception e) {
            throw new RuntimeException("将java对象转为Map失败", e);
        }

        //3.转xml
        String xml = this.mapToXml(req);

        log.info("生成微信发送定额红包接口参数:\n{}", xml);

        //4.发送
        String r = new Post("https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack")
                .header(Http.NAME_CONTENT_TYPE, Http.CONTENT_TYPE_XML)
                .sslSocketFactory(getSslSocketFactory(this.certPath, this.merchantNo))
                .hostnameVerifier(new BrowserCompatHostnameVerifier())
                .body(xml).post();

        log.info("调用微信定额红包接口成功,返回:\n{}", r);

        //5.将从API返回的XML数据映射到Java对象
        Map returnMap = this.xmlToMap(r);

        RedpackResult redpackResult = FastBeanUtils.fastPopulate(RedpackResult.class, returnMap);

        //7.验签就不用了,微信文档坑爹,根本就没返回签名字段...
        return redpackResult;
    }

    /**
     * 发送微信裂变红包
     *
     * @param redpack
     */
    public RedpackResult send(GroupRedpack redpack) {
        redpack.setWxappid(this.appid);
        redpack.setMch_id(this.merchantNo);
        redpack.setNonce_str(Uuid.buildBase64UUID());

        String sign = WechatPaySignature.sign(redpack, this.key);
        redpack.setSign(sign);

        Map<String, String> req;
        try {
            req = FastBeanUtils.describe(redpack);
            req.remove("class");
        } catch (Exception e) {
            throw new RuntimeException("将java对象转为Map失败", e);
        }

        //3.转xml
        String xml = this.mapToXml(req);

        log.info("生成微信发送裂变红包接口参数:\n{}", xml);

        //4.发送
        String r = new Post("https://api.mch.weixin.qq.com/mmpaymkttransfers/sendgroupredpack")
                .header(Http.NAME_CONTENT_TYPE, Http.CONTENT_TYPE_XML)
                .sslSocketFactory(getSslSocketFactory(this.certPath, this.merchantNo))
                .hostnameVerifier(new BrowserCompatHostnameVerifier())
                .body(xml).post();
        log.info("调用微信裂变红包接口成功,返回:\n{}", r);

        //5.将从API返回的XML数据映射到Java对象
        Map returnMap = this.xmlToMap(r);

        RedpackResult redpackResult = FastBeanUtils.fastPopulate(RedpackResult.class, returnMap);

        //7.验签就不用了,微信文档坑爹,根本就没返回签名字段...

        return redpackResult;
    }

    private static SSLSocketFactory getSslSocketFactory(String certPath, String password) {
        if (sslSocketFactory == null) {
            synchronized (WechatRedpack.class) {
                if (sslSocketFactory == null) {
                    sslSocketFactory = new PKC12SslSocketFacotryBuilder(certPath, password).build();
                }
            }
        }
        return sslSocketFactory;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setCertPath(String certPath) {
        this.certPath = certPath;
    }
}
