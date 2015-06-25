package org.treeleaf.common.bean;

import java.lang.reflect.Method;

/**
 * 类属性映射<br>
 * 用于存放属性的get/set等信息
 *
 * @author yaoshuhong
 * @date 2014-8-31 上午12:10:16
 */
public class PropertiesEntry {

    private String name;

    private Class<?> type;

    private Method get;

    private Method set;

    public PropertiesEntry(String name, Class<?> type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Method getGet() {
        return get;
    }

    public void setGet(Method get) {
        this.get = get;
    }

    public Method getSet() {
        return set;
    }

    public void setSet(Method set) {
        this.set = set;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }
}
