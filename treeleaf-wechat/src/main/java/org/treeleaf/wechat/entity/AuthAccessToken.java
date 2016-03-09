package org.treeleaf.wechat.entity;

/**
 * 使用微信网页跳转授权后返回的code去请求accesstoken返回的数据
 * <p>
 * Created by yaoshuhong on 2016/3/9.
 */
public class AuthAccessToken extends WeChatBaseInfo {

    /**
     * 微信js sdk的access_token
     */
    private String access_token;

    /**
     * 超时时间(秒)
     */
    private Integer expires_in;

    /**
     * 用户刷新access_token
     */
    private String refresh_token;

    /**
     * 微信用户的openid
     */
    private String openid;

    /**
     * 用户授权的作用域，使用逗号（,）分隔
     */
    private String scope;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public Integer getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
