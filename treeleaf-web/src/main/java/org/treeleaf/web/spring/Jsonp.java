package org.treeleaf.web.spring;

import org.treeleaf.web.Json;

/**
 * @Author leaf
 * 2016/3/5 0005 15:19.
 */
public class Jsonp extends Json {

    public Jsonp() {
    }

    public Jsonp(String retCode, String msg) {
        super(retCode, msg);
    }

    public Jsonp(Object data) {
        super(data);
    }
}
