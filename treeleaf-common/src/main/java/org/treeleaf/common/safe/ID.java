package org.treeleaf.common.safe;

/**
 * Created by yaoshuhong on 2016/2/19.
 */
public abstract class ID {

    /**
     * 生成一个有序,分布式唯一,且24位长度的id(只包含数字和字母,字母都为小写字母)
     *
     * @return
     */
    public static String get() {
        return org.apache.commons.codec.binary.Hex.encodeHexString(ObjectId.get().toByteArray());
    }

    /**
     * 生成一个有序,分布式唯一,且16位长度的短id(url安全的,区分大小写,包含"-")
     *
     * @return
     */
    public static String getShort() {
        return Base64.encodeURLSafe(ObjectId.get().toByteArray());
    }
}
