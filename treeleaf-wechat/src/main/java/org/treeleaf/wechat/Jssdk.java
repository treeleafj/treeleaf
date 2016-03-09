package org.treeleaf.wechat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.treeleaf.common.http.Get;
import org.treeleaf.common.http.Post;
import org.treeleaf.common.json.Jsoner;
import org.treeleaf.common.safe.Sha;
import org.treeleaf.common.safe.Uuid;
import org.treeleaf.wechat.entity.*;

import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信js sdk接口调用封装
 * <p>
 * Created by yaoshuhong on 2016/3/9.
 */
public class Jssdk {

    private static Logger log = LoggerFactory.getLogger(Jssdk.class);

    /**
     * 微信js sdk的appid
     */
    private String appid;

    /**
     * 微信js sdk的secret
     */
    private String secret;

    /**
     * 获取微信js sdk的access_token
     *
     * @return
     */
    public AccessToken access_token() {
        String s = new Get("https://api.weixin.qq.com/cgi-bin/token")
                .param("grant_type", "client_credential")
                .param("appid", appid)
                .param("secret", secret)
                .get();

        log.info("调用微信获取accessToken接口,返回:{}", s);

        return Jsoner.toObj(s, AccessToken.class);
    }

    /**
     * 通过微信网页授权返回的code查询access_token
     *
     * @param code 微信网页授权返回的code
     * @return
     */
    public AuthAccessToken authAccessToken(String code) {
        String s = new Post("https://api.weixin.qq.com/sns/oauth2/access_token")
                .param("appid", appid)
                .param("secret", secret)
                .param("code", code)
                .param("grant_type", "authorization_code")
                .post();

        log.info("调用微信网页授权access_token接口,返回:{}", s);

        return Jsoner.toObj(s, AuthAccessToken.class);
    }

    /**
     * 获取微信js sdk的ticket
     *
     * @param access_token 微信js sdk的access_token
     * @param type         类型,不传默认是jsapi, 其它可选的为wx_card
     * @return
     */
    public Ticket ticket(String access_token, String... type) {
        String s = new Get("https://api.weixin.qq.com/cgi-bin/ticket/getticket")
                .param("access_token", access_token)
                .param("type", type.length > 0 ? type[0] : "jsapi")
                .get();

        log.info("调用微信获取ticket接口,返回:{}", s);
        return Jsoner.toObj(s, Ticket.class);
    }

    /**
     * 获取已经微信页面授权的微信用户信息
     *
     * @param access_token 微信js sdk的access_token
     * @param openid       页面授权后返回的openid
     * @return
     */
    public SnsUserInfo snsuserinfo(String access_token, String openid) {
        String result = new Post("https://api.weixin.qq.com/sns/userinfo")
                .param("access_token", access_token)
                .param("openid", openid)
                .param("lang", "zh_CN")
                .post();

        log.info("调用微信获取用户基本信息接口,返回:{}", result);
        return Jsoner.toObj(result, SnsUserInfo.class);
    }

    /**
     * 获取已关注了公众号的微信用户信息
     *
     * @param access_token 微信js sdk的access_token
     * @param openid       已关注公众号的微信用户openid
     * @return
     */
    public UserInfo userinfo(String access_token, String openid) {
        String s = new Get("https://api.weixin.qq.com/cgi-bin/user/info")
                .param("access_token", access_token)
                .param("openid", openid)
                .param("lang", "zh_CN")
                .get();
        return Jsoner.toObj(s, UserInfo.class);
    }

    /**
     * 发送微信消息模版
     *
     * @param access_token 微信js sdk的access_token
     * @param templateId   消息模版的id
     * @param openId       微信用户的openid
     * @param url          点击微信消息时跳转的地址
     * @param data         模版的填装数据
     * @return
     */
    public SendTemplateResult sendtemplate(String access_token, String templateId, String openId, String url, Map data) {
        String address = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + access_token;

        Map param = new HashMap<>();
        param.put("data", data);
        param.put("template_id", templateId);
        param.put("url", url);
        param.put("touser", openId);

        String body = Jsoner.toJson(param);

        String result = new Post(address).body(body).post();

        log.info("调用微信发送模版消息接口,返回:{}", result);

        return Jsoner.toObj(result, SendTemplateResult.class);
    }

    /**
     * 获取微信页面wx.config的初始化代码
     *
     * @param ticket 微信的授权票据
     * @param url    授权页面地址
     * @param debug  是否开启debug模式
     * @param md     是否使用CMD或者AMD模式
     * @return
     */
    public String wxconfig(String ticket, String url, boolean debug, boolean md) {

        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
        String nonce_str = Uuid.buildBase64UUID();

        //注意这里参数名必须全部小写，且必须有序
        String source = "jsapi_ticket=" + ticket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp + "&url=" + url;

        log.debug("微信签名:{}", source);

        //开始微信签名
        byte[] bytes = Sha.sha1(source);

        Formatter formatter = new Formatter();
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }

        String sign = formatter.toString();
        formatter.close();

        String js;
        if (md) {
            js = "define(function (require, exports, module) {\n" +
                    "    var wx = require('jweixin');\n" +
                    "    var obj = {\n" +
                    "        initWxConfig : function() {\n" +
                    "            wx.config({\n" +
                    "                debug: %s,\n" +
                    "                appId: '%s',\n" +
                    "                timestamp: '%s',\n" +
                    "                nonceStr: '%s',\n" +
                    "                signature: '%s',\n" +
                    "                jsApiList: ['onMenuShareTimeline','onMenuShareAppMessage','onMenuShareQQ','onMenuShareWeibo','onMenuShareQZone']\n" +
                    "            });\n" +
                    "        }\n" +
                    "    };\n" +
                    "   module.exports = obj;\n" +
                    "});";
        } else {
            js = "var wxConfig = {\n" +
                    "        initWxConfig : function() {\n" +
                    "            wx.config({\n" +
                    "                debug: %s,\n" +
                    "                appId: '%s',\n" +
                    "                timestamp: '%s',\n" +
                    "                nonceStr: '%s',\n" +
                    "                signature: '%s',\n" +
                    "                jsApiList: ['onMenuShareTimeline','onMenuShareAppMessage','onMenuShareQQ','onMenuShareWeibo','onMenuShareQZone']\n" +
                    "            });\n" +
                    "        }\n" +
                    "    };\n";
        }

        return String.format(js, String.valueOf(debug), this.getAppid(), timestamp, nonce_str, sign);
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getOauth2Url() {
        return "https://open.weixin.qq.com/connect/oauth2/authorize";
    }

}
