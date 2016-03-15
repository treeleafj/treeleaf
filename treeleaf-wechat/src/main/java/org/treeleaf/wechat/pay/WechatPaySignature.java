package org.treeleaf.wechat.pay;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.treeleaf.common.safe.Hex;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 微信签名工具
 *
 * @Author leaf
 * 2015/9/4 0004 15:35.
 */
public class WechatPaySignature {

    private static Logger log = LoggerFactory.getLogger(WechatPaySignature.class);

    public static String sign(Object o, String key) {

        List<String> list;
        if (o instanceof Map) {
            list = map2List((Map<String, String>) o);
        } else {
            list = obj2List(o);
        }

        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < size; i++) {
            sb.append(arrayToSort[i]);
        }

        String result = sb.toString();
        result += "key=" + key;
        result = Hex.md5(result).toUpperCase();

        return result;
    }

    private static List<String> map2List(Map<String, String> map) {
        List<String> list = new ArrayList<String>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (StringUtils.isNotEmpty(entry.getValue())) {
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        return list;
    }

    private static List<String> obj2List(Object o) {
        List<String> list = new ArrayList<>();
        Class cls = o.getClass();
        Field[] fields = cls.getDeclaredFields();

        try {
            for (Field f : fields) {
                f.setAccessible(true);
                if (f.get(o) != null && !"".equals(f.get(o))) {
                    list.add(f.getName() + "=" + f.get(o) + "&");
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}


