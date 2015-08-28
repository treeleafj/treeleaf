package org.treeleaf.web;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回让前台跳转的结果(response.sendRedirect)
 * <p>
 * Created by yaoshuhong on 2015/5/30.
 */
public class Redirect implements Result {

    /**
     * 跳转地址
     */
    private String path;

    /**
     * 参数,对于web会自动组成?a=1&b=2&c=3这样的方式
     */
    private Map<String, String> param = new HashMap<String, String>();

    /**
     * @param path 要跳转的路径
     */
    public Redirect(String path) {
        this.path = path;
    }

    /**
     * @param path  要跳转的路径
     * @param param 地址上的参数,对于web会自动组成?a=1&b=2&c=3这样的方式
     */
    public Redirect(String path, Map<String, String> param) {
        this.path = path;
        this.param = param;
    }

    public Map<String, String> getParam() {
        return param;
    }

    public void setParam(Map<String, String> param) {
        this.param = param;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
