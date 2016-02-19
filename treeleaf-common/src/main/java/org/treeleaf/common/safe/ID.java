package org.treeleaf.common.safe;

/**
 * Created by yaoshuhong on 2016/2/19.
 */
public abstract class ID {

    /**
     * 生成一个有序,分布式唯一,且16位长度的id
     *
     * @return
     */
    public static String get() {
        return Base64.encodeURLSafe(ObjectId.get().toByteArray());
    }

}
