package org.treeleaf.wechat.js.entity;

/**
 * Created by yaoshuhong on 2016/3/9.
 */
public class SendTemplateResult extends WeChatBaseInfo {

    /**
     * 发送
     */
    private Integer msgid;

    public Integer getMsgid() {
        return msgid;
    }

    public void setMsgid(Integer msgid) {
        this.msgid = msgid;
    }
}
