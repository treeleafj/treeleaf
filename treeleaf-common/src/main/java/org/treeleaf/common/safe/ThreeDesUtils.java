package org.treeleaf.common.safe;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.Key;

/**
 * 3Des加密解密
 * <p/>
 * Created by yaoshuhong on 2015/4/27.
 */
public class ThreeDesUtils {


    private static final String algorithm = "DESede";

    private final static String CHARSET = "UTF-8";

    /**
     * 加密
     *
     * @param value  加密的字符串
     * @param keyStr 加密的密钥
     * @return
     */
    public static String encrypt(String value, String keyStr) throws Exception {

        if (value == null || "".equals(value)) {
            return null;
        }

        Key key = getKey(keyStr.getBytes(CHARSET));

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] re = cipher.doFinal(value.getBytes(CHARSET));

        return byteArr2HexStr(re);
    }

    /**
     * 3DES 解密
     *
     * @param value  解密的字符串
     * @param keyStr 解密的密钥
     * @return
     * @throws Exception
     */
    public static String decrypt(String value, String keyStr) throws Exception {

        if (value == null || "".equals(value)) {
            return null;
        }
        Key key = getKey(keyStr.getBytes(CHARSET));

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] re = cipher.doFinal(hexStr2ByteArr(value));

        return new String(re, CHARSET);

    }

    private static Key getKey(byte[] arrBTmp) {
        // 创建一个空的8位字节数组（默认值为0）
        byte[] arrB = new byte[24];
        // 将原始字节数组转换为8位
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }
        // 生成密钥
        Key key = new SecretKeySpec(arrB, algorithm);
        return key;
    }

    /**
     * 将byte数组转换为表示16进制值的字符串
     *
     * @param arrB 需要转换的byte数组
     * @return 转换后的字符串
     * @throws Exception 本方法不处理任何异常，所有异常全部抛出
     */
    public static String byteArr2HexStr(byte[] arrB) {
        int iLen = arrB.length;
        // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = arrB[i];
            // 把负数转换为正数
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            // 小于0F的数需要在前面补0
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }

    /**
     * 将表示16进制值的字符串转换为byte数组
     *
     * @param strIn 需要转换的字符串
     * @return 转换后的byte数组
     */
    public static byte[] hexStr2ByteArr(String strIn) {
        byte[] arrB = null;
        try {
            arrB = strIn.getBytes(CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        int iLen = arrB.length;
        // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }

    public static void main(String[] args) {
        try {
            String str = "cger5fg1egh1f2e36f14hgf1ge1f1nj7t8yh2f2ws";
            String key = "5er1g12f2dv1g4r4ee2d1";
            String encrptStr = ThreeDesUtils.encrypt(str,
                    key);
            System.out.println("加密：" + encrptStr);
            System.out.println("解密:" + decrypt("bfe1fd88e17dc23d401ee7562efdcc2991ad3cc2b8144a4a96bb0a5092010b90d478d2ca7d20ff96", key));
            /*String decrptStr = ThreeDesUtils.decrypt("d244af7b2e1ae932",
                    "botianpasswordkey20130523");

			System.out.println("解密：" + decrptStr);
*/
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
