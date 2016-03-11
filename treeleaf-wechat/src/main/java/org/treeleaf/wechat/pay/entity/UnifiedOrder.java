package org.treeleaf.wechat.pay.entity;

/**
 * @Author leaf
 * 2016/3/11 0011 20:58.
 */
public class UnifiedOrder {

    /**
     * 微信分配的公众账号ID
     */
    private String appid;

    /**
     * 微信支付分配的商户号
     */
    private String mch_id;

    /**
     * 终端设备号(门店号或收银设备ID)，注意：PC网页或公众号内支付请传"WEB"
     */
    private String device_info;

    /**
     * 随机字符串，不长于32位
     */
    private String nonce_str;

    /**
     * 签名
     */
    private String sign;

    /**
     * 商品或支付单简要描述
     */
    private String body;

    /**
     * 商户系统内部的订单号,32个字符内、可包含字母
     */
    private String out_trade_no;

    /**
     * 符合ISO 4217标准的三位字母代码，默认人民币：CNY
     */
    private String fee_type;

    /**
     * 订单总金额，只能为整数
     */
    private String total_fee;

    /**
     * APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP
     */
    private String spbill_create_ip;

    /**
     * 接收微信支付异步通知回调地址
     */
    private String notify_url;

    /**
     * 取值如下：JSAPI，NATIVE，APP，WAP
     */
    private String trade_type;

    /**
     * 用户标识,trade_type=JSAPI，此参数必传
     */
    private String openid;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}