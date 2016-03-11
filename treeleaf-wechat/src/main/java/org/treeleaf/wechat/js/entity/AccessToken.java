package org.treeleaf.wechat.js.entity;

/**
 * Created by yaoshuhong on 2016/3/9.
 */
public class AccessToken extends WeChatBaseInfo {

    /**
     * 微信js sdk的access_token
     */
    private String access_token;

    /**
     * access_token的超时时间,单位为秒
     */
    private int expires_in;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }
}
