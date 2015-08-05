package org.treeleaf.web;

/**
 * Http 上下文类型
 * <p/>
 * Created by yaoshuhong on 2015/7/27.
 */
public enum ContentType {

    CONTENT_TYPE_TEXT("text/palin"),

    CONTENT_TYPE_HTML("text/html"),

    CONTENT_TYPE_JSON("application/json");

    private String value;

    ContentType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
