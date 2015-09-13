package org.treeleaf.common.safe;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * Des加密工具
 *
 * @Author leaf
 * 2015/9/13 0013 17:00.
 */
public class Des {


    /**
     * 对数据进行Des加密
     *
     * @param value 明文
     * @param key   密钥
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] value, byte[] key) throws Exception {
        DESKeySpec desKeySpec = new DESKeySpec(key);//前8个字节做为密钥
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(value);
    }


    /**
     * 对数据进行Des加密
     *
     * @param value 明文
     * @param key   密钥
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(String value, byte[] key) throws Exception {
        DESKeySpec desKeySpec = new DESKeySpec(key);//前8个字节做为密钥
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(value.getBytes("utf-8"));
    }

    /**
     * 对数据进行Des解密
     *
     * @param value 密文
     * @param key   密钥
     * @return 明文
     * @throws Exception
     */
    public static byte[] decrypt(byte[] value, byte[] key) throws Exception {
        DESKeySpec desKeySpec = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(value);
    }

    /**
     * 对数据进行Des解密
     *
     * @param value 密文
     * @param key   密钥
     * @return 明文
     * @throws Exception
     */
    public static byte[] decrypt(String value, byte[] key) throws Exception {
        DESKeySpec desKeySpec = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(value.getBytes("utf-8"));
    }
}
