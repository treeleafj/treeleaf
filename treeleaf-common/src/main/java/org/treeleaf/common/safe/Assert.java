package org.treeleaf.common.safe;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Collection;
import java.util.Map;

public class Assert {

    /**
     * 断言传入的值为true
     *
     * @param v
     * @param msg
     */
    public static void isTrue(boolean v, String msg) {
        if (!v) {
            throw new AssertException(msg);
        }
    }

    /**
     * 断言传入的值不为null
     *
     * @param v
     * @param msg
     */
    public static void notNull(Object v, String msg) {
        if (v == null) {
            throw new AssertException(msg);
        }
    }

    /**
     * 断言传入的值不为空白
     *
     * @param v
     * @param msg
     */
    public static void hasText(String v, String msg) {
        if (StringUtils.isBlank(v)) {
            throw new AssertException(msg);
        }
    }

    /**
     * 断言传入的字符串不包含某值
     *
     * @param str
     * @param s
     * @param msg
     */
    public static void doesNotContain(String str, String s, String msg) {
        if (StringUtils.isNotEmpty(str) && StringUtils.isNotEmpty(s) && str.contains(s)) {
            throw new AssertException(msg);
        }
    }

    /**
     * 断言传入的字符串/集合/数组不为空
     *
     * @param v
     * @param msg
     */
    public static void notEmpty(Object v, String msg) {

        if (v == null) {
            throw new AssertException(msg);
        }

        if (v instanceof String) {
            if (((String) v).isEmpty()) {
                throw new AssertException(msg);
            }
        } else if (v instanceof Collection) {
            Collection c = (Collection) v;
            if (c.size() <= 0) {
                throw new AssertException(msg);
            }
        } else if (v instanceof Map) {
            if (((Map) v).size() <= 0) {
                throw new AssertException(msg);
            }
        } else if (v.getClass().isArray()) {
            if (ArrayUtils.isEmpty((Object[]) v)) {
                throw new AssertException(msg);
            }
        }
    }

    /**
     * 字符串大于或等于指定的长度
     *
     * @param s       字符串
     * @param max     长度
     * @param message 错误信息
     */
    public static void isGreaterThanOrEqual(String s, int max, String message) {
        if (s == null || s.length() <= max) {
            throw new AssertException(message);
        }
    }

    /**
     * 字符串小于或等于指定的长度
     *
     * @param s       字符串
     * @param min     长度
     * @param message 错误信息
     */
    public static void isLessThanOrEqual(String s, int min, String message) {
        if (s == null || s.length() > min) {
            throw new AssertException(message);
        }
    }

    /**
     * 字符串长度为于min与max之间,允许等于max或min
     *
     * @param s       字符窜
     * @param max     最大长度
     * @param min     最小长度
     * @param message 错误信息
     */
    public static void isBetween(String s, int max, int min, String message) {
        if (s == null || (s.length() > max || s.length() < min)) {
            throw new AssertException(message);
        }
    }

    /**
     * 字符串为数字
     *
     * @param s
     * @param message
     */
    public static void isNumber(String s, String message) {
        if (!NumberUtils.isNumber(s)) {
            throw new AssertException(message);
        }
    }

    /**
     * 给定的数字小于或等于指定的数字
     *
     * @param number1 给定的数字
     * @param number2 指定的数字
     * @param message
     */
    public static void numberLessOrEqual(int number1, int number2, String message) {
        if (number1 > number2) {
            throw new AssertException(message);
        }
    }

}
