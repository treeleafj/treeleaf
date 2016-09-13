package org.treeleaf.web;


import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.treeleaf.common.exception.RetCode;
import org.treeleaf.common.json.Jsoner;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 返回json数据的基本结构
 * <p>
 * Created by yaoshuhong on 2015/4/29.
 */
public class Json extends Text {

    private static Logger log = LoggerFactory.getLogger(Json.class);

    /**
     * 返回码
     */
    private String retCode = RetCode.OK;

    /**
     * 信息描述
     */
    private String msg;

    /**
     * 数据
     */
    private Object data;

    /**
     * 返回一个默认成功的json格式
     */
    public Json() {
    }

    /**
     * 返回一个特定返回码和特定描述的的json格式
     *
     * @param retCode
     * @param msg
     */
    public Json(String retCode, String msg) {
        this.retCode = retCode;
        this.msg = msg;
    }

    /**
     * 返回一个成功且带有特定数据的json格式
     *
     * @param data
     */
    public Json(Object data) {
        this.data = data;
    }

    @Override
    public String getContent() {

        Map map;

        if (this.data != null) {

            if (this.data instanceof Map) {
                map = (Map) this.data;
            } else if (this.data instanceof Collection || this.data.getClass().isArray()) {
                map = new HashMap<>();
                map.put("array", this.data);
            } else {
                try {
                    map = PropertyUtils.describe(this.data);
                    map.remove("class");
                } catch (Exception e) {
                    log.error("将Json.data转map失败", e);
                    throw new RuntimeException(e);
                }
            }

        } else {
            map = new HashMap<>();
        }

        map.put("retCode", retCode);

        if (msg != null) {
            map.put("msg", msg);
        }
        return Jsoner.toJson(map);
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
