package org.treeleaf.wechat.js.entity;

/**
 * Created by yaoshuhong on 2016/3/9.
 */
public class WeChatBaseInfo {

    /**
     * 错误时返回的错误代码
     */
    private Integer errcode;

    /**
     * 错误时返回的错误描述信息
     */
    private String errmsg;

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
