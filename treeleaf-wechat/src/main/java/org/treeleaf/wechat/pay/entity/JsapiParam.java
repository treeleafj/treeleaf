package org.treeleaf.wechat.pay.entity;

/**
 * @Author leaf
 * 2016/3/12 0012 0:42.
 */
public class JsapiParam {

    /**
     * 应用id
     */
    private String appId;

    /**
     * 时间戳
     */
    private String timeStamp;

    /**
     * 随机字符窜
     */
    private String nonceStr;

    /**
     * 支付预处理id
     */
    private String pk;

    /**
     * 签名类型
     */
    private String signType;

    /**
     * 支付签名
     */
    private String paySign;

    /**
     * 订单号
     */
    private String orderno;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getPaySign() {
        return paySign;
    }

    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }
}
