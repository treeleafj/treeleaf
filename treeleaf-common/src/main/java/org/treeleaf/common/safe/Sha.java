package org.treeleaf.common.safe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author leaf
 * 2015/9/12 0012 1:38.
 */
public class Sha {

    private static Logger log = LoggerFactory.getLogger(Sha.class);

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
            throw new RuntimeException("加密失败", e);
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

    /**
     * sha-1加密
     *
     * @param bts
     * @return
     */
    public static byte[] sha1(byte[] bts) {
        MessageDigest crypt = null;
        try {
            crypt = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("加密失败", e);
        }
        crypt.reset();
        crypt.update(bts);
        byte[] digest = crypt.digest();
        return digest;
    }

    /**
     * sha-1加密
     *
     * @param bts
     * @return
     */
    public static byte[] sha1(String bts) {
        try {
            return sha1(bts.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("字符串转换为utf-8的byte时异常", e);
        }
    }

    /**
     * 将byte数据转为16进制
     *
     * @param bts
     * @return
     */
    public static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }
}
