package org.treeleaf.common.safe;

import java.util.UUID;

/**
 * UUID生成工具
 * <p>
 * Created by yaoshuhong on 2015/5/7.
 */
public class Uuid {

    /**
     * 生成普通32位UUID字符串
     *
     * @return 32位UUID字符串
     */
    public static String buildUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    /**
     * 生成压缩后的UUID字符串,同时是基于web安全的字符串
     *
     * @return 22位UUID字符串
     */
    public static String buildBase64UUID() {
        UUID uuid = UUID.randomUUID();
        return compressedUUID(uuid);
    }


    private static String compressedUUID(UUID uuid) {
        byte[] byUuid = new byte[16];
        long least = uuid.getLeastSignificantBits();
        long most = uuid.getMostSignificantBits();
        long2bytes(most, byUuid, 0);
        long2bytes(least, byUuid, 8);
        return Base64.encodeURLSafe(byUuid);
    }

    private static void long2bytes(long value, byte[] bytes, int offset) {
        for (int i = 7; i > -1; i--) {
            bytes[offset++] = (byte) ((value >> 8 * i) & 0xFF);
        }
    }
}
