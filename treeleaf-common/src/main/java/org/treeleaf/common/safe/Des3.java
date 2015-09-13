package org.treeleaf.common.safe;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.security.NoSuchAlgorithmException;

/**
 * 3Des加解密工具
 * <p>
 * Created by yaoshuhong on 2015/7/7.
 */
public abstract class Des3 {

    private static final String CHARSET = "UTF-8";

    private static final String ALGORITHM = "DESede/ECB/PKCS5Padding";

    /**
     * 3des(ECB模式,PKCS5Padding补码)加密数据,并转为Base64
     *
     * @param src 明文数据
     * @param key 可以被base64的密钥(长度需要24位)
     * @return
     * @throws Exception
     */
    public static String encryptToBase64(String src, String key) throws Exception {
        byte[] byteSrc = src.getBytes(CHARSET);
        byte[] byteKey = Base64.decode(key);
        return Base64.encode(encrypt(byteSrc, byteKey));
    }

    /**
     * 3des(ECB模式,PKCS5Padding补码)加密数据
     *
     * @param value 明文数据
     * @param key   密钥
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] value, byte[] key) throws Exception {

        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
        SecretKey sec = keyFactory.generateSecret(spec);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, sec);
        return cipher.doFinal(value);
    }

    /**
     * 3des(ECB模式,PKCS5Padding补码)解密数据
     *
     * @param src 密文数据(可被Base64解码)
     * @param key 密钥
     * @return
     * @throws Exception
     */
    public static String decryptByBase64(String src, String key) throws Exception {
        byte[] byteKey = Base64.decode(key);
        byte[] data = Base64.decode(src);
        return new String(decrypt(data, byteKey), CHARSET);
    }

    /**
     * 3des(ECB模式,PKCS5Padding补码)解密数据
     *
     * @param src 密文数据
     * @param key 密钥
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] src, byte[] key) throws Exception {

        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
        SecretKey sec = keyFactory.generateSecret(spec);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, sec);
        return cipher.doFinal(src);
    }

    /**
     * 构建3des的密钥
     *
     * @return
     * @throws java.security.NoSuchAlgorithmException
     */
    public static byte[] build3DesKey() throws NoSuchAlgorithmException {
        KeyGenerator keygen = KeyGenerator.getInstance("DESede");
        return keygen.generateKey().getEncoded();
    }

    /**
     * 构建3des的密钥,并转为64位
     *
     * @return
     * @throws java.security.NoSuchAlgorithmException
     */
    public static String build3DesKeyToBase64() throws NoSuchAlgorithmException {
        byte[] key = build3DesKey();
        return Base64.encode(key);
    }
}
