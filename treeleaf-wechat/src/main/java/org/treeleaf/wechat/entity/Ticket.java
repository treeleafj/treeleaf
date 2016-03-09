package org.treeleaf.wechat.entity;

/**
 * Created by yaoshuhong on 2016/3/9.
 */
public class Ticket extends WeChatBaseInfo {

    /**
     * 微信js sdk的access_token
     */
    private String ticket;

    /**
     * access_token的超时时间,单位为秒
     */
    private int expires_in;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }
}
