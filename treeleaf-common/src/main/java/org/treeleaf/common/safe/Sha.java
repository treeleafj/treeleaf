package org.treeleaf.common.safe;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author leaf
 * 2015/9/12 0012 1:38.
 */
public class Sha {


    /**
     * 对字符串进行摘要,摘要算法使用SHA-256
     *
     * @param bts 要加密的字符串的byte数组
     * @return 16进制表示的大写字符串 长度一定是8的整数倍
     */
    public static byte[] sha256(byte[] bts) {
        MessageDigest md = null;
        byte[] result = null;
        try {
            //定义摘要算法为SHA-256
            md = MessageDigest.getInstance("SHA-256");
            md.update(bts);
            result = md.digest();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return result;
    }


    /**
     * 对字符串进行摘要,摘要算法使用SHA-256
     *
     * @param bts 要加密的字符串
     * @return 16进制表示的大写字符串 长度一定是8的整数倍
     */
    public static byte[] sha256(String bts) {
        try {
            return sha256(bts.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("字符串转换为utf-8的byte时异常", e);
        }
    }
}
