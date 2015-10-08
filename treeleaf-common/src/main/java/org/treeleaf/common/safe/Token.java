package org.treeleaf.common.safe;

/**
 * token构建工具,基于Hex算法,所以构建出来的token存在重复的情况
 *
 * @Author leaf
 * 2015/9/3 0003 19:43.
 */
public class Token {

    private String salt = "";

    public Token() {
    }

    /**
     * 构建带有盐值增加安全性的token
     *
     * @param salt 盐值
     */
    public Token(String salt) {
        this.salt = salt;
    }

    /**
     * 构建token
     *
     * @param src 拼装要素
     * @return
     */
    public String buildToken(Object... src) {
        StringBuilder sb = new StringBuilder();
        for (Object o : src) {
            sb.append(o);
        }
        sb.append(salt);
        return Hex.md5(sb.toString());
    }

}
