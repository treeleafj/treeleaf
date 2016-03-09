package org.treeleaf.wechat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.treeleaf.common.http.Get;
import org.treeleaf.common.http.Post;
import org.treeleaf.common.json.Jsoner;
import org.treeleaf.wechat.entity.*;

import java.util.HashMap;
import java.util.Map;

/**
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
    public SendTemplateResult sendtempldate(String access_token, String templateId, String openId, String url, Map data) {
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
}
