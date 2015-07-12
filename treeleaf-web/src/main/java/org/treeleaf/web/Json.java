package org.treeleaf.web;


import org.treeleaf.web.spring.CommonConstant;

/**
 * 返回json数据的基本结构
 * <p/>
 * Created by yaoshuhong on 2015/4/29.
 */
public class Json implements Result{

    /**
     * 返回码
     */
    private String retCode = CommonConstant.DEFAULT_SUCCESS_RETCODE;

    /**
     * 信息描述
     */
    private String msg;

    /**
     * 数据
     */
    private Object data;

    public Json() {
    }

    public Json(String retCode, String msg) {
        this.retCode = retCode;
        this.msg = msg;
    }

    public Json(Object data) {
        this.data = data;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
