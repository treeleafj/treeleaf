package org.treeleaf.common.safe;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Collection;
import java.util.Map;

/**
 * 扩展Assert断言类,提供丰富的异常信息能力
 */
public class AssertEx extends Assert {

    /**
     * 断言传入的值为true
     *
     * @param v
     */
    public static AssertResult isTrue(boolean v) {
        return new AssertResult(v);
    }

    /**
     * 断言传入的值不为null
     *
     * @param v
     */
    public static AssertResult notNull(Object v) {
        return new AssertResult(v != null);
    }

    /**
     * 断言传入的值不为空白
     *
     * @param v
     */
    public static AssertResult hasText(String v) {
        return new AssertResult(StringUtils.isNotBlank(v));
    }

    /**
     * 断言传入的字符串不包含某值
     *
     * @param str
     * @param s
     */
    public static AssertResult doesNotContain(String str, String s) {
        boolean b = StringUtils.isEmpty(str) || StringUtils.isEmpty(s) || !str.contains(s);
        return new AssertResult(b);
    }

    /**
     * 断言传入的字符串/集合/数组不为空
     *
     * @param v
     */
    public static AssertResult notEmpty(Object v) {

        if (v == null) {
            return new AssertResult(false);
        }

        if (v instanceof String) {
            return new AssertResult(!((String) v).isEmpty());

        } else if (v instanceof Collection) {
            return new AssertResult(((Collection) v).size() > 0);

        } else if (v instanceof Map) {
            return new AssertResult(((Map) v).size() > 0);

        } else if (v.getClass().isArray()) {
            return new AssertResult(((Object[]) v).length > 0);
        }

        return new AssertResult(false);
    }

    /**
     * 字符串大于或等于指定的长度
     *
     * @param s   字符串
     * @param max 长度
     */
    public static AssertResult isGreaterThanOrEqual(String s, int max) {
        return new AssertResult(s != null && s.length() >= max);
    }

    /**
     * 字符串小于或等于指定的长度
     *
     * @param s   字符串
     * @param min 长度
     */
    public static AssertResult isLessThanOrEqual(String s, int min) {
        return new AssertResult(s != null && s.length() <= min);
    }

    /**
     * 字符串长度为于min与max之间,允许等于max或min
     *
     * @param s   字符窜
     * @param max 最大长度
     * @param min 最小长度
     */
    public static AssertResult isBetween(String s, int max, int min) {

        return new AssertResult(s != null && s.length() <= max && s.length() >= min);
    }

    /**
     * 字符串为数字
     *
     * @param s
     */
    public static AssertResult isNumber(String s) {
        return new AssertResult(NumberUtils.isNumber(s));
    }
}
