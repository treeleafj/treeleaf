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
 * 框架Json对象, 用于兼容Extjs框架
 *
 * @Author leaf
 * 2015/11/23 0023 0:44.
 */
public class ExtJson extends Json {

    private static Logger log = LoggerFactory.getLogger(ExtJson.class);

    public ExtJson() {
    }

    public ExtJson(String retCode, String msg) {
        super(retCode, msg);
    }

    public ExtJson(Object data) {
        super(data);
    }

    @Override
    public String getContent() {
        Object map;
        if (this.getData() != null) {
            if (this.getData() instanceof Map) {
                map = (Map) this.getData();
            } else if (!(this.getData() instanceof Collection) && !this.getData().getClass().isArray()) {
                try {
                    map = PropertyUtils.describe(this.getData());
                    ((Map) map).remove("class");
                } catch (Exception var3) {
                    log.error("将Json.data转map失败", var3);
                    throw new RuntimeException(var3);
                }
            } else {
                map = new HashMap();
                ((Map) map).put("array", this.getData());
            }
        } else {
            map = new HashMap();
        }

        ((Map) map).put("retCode", this.getRetCode());
        ((Map) map).put("success", RetCode.OK.equals(this.getRetCode()));
        if (this.getMsg() != null) {
            ((Map) map).put("msg", this.getMsg());
        }

        return Jsoner.toJson(map);
    }
}
