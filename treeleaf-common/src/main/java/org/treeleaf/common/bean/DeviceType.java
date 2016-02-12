package org.treeleaf.common.bean;

/**
 * 设备类型
 *
 * @Author leaf
 * 2016/2/12 0012 19:45.
 */
public enum DeviceType {

    /**
     * PC电脑
     */
    PC("pc", "1"),

    /**
     * 手机
     */
    MOBILE("mobile", "2"),

    /**
     * 平板
     */
    PAD("pad", "3"),

    /**
     * 手表
     */
    WATCH("watch", "4");

    private String desc;

    private String code;

    DeviceType(String desc, String code) {
        this.desc = desc;
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public String getCode() {
        return code;
    }
}
