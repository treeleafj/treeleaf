package org.treeleaf.common.exception;

/**
 * 通用错误码
 *
 * @Author leaf
 * 2015/8/28 0028 1:08.
 */
public abstract class RetCode {

    /**
     * 成功
     */
    public final static String OK = "000000";

    /**
     * 未知异常
     */
    public final static String FAIL_UNKNOWN = "999999";

    /**
     * 逻辑错误
     */
    public final static String FAIL_LOGIC = "999998";

    /**
     * 参数异常
     */
    public final static String FAIL_PARAM = "999997";

    /**
     * 缓存操作异常
     */
    public final static String FAIL_CACHE = "999996";

    /**
     * 数据库操作异常
     */
    public final static String FAIL_DB = "999995";

    /**
     * 远程访问异常
     */
    public final static String FAIL_REMOTE = "999994";

    /**
     * 未登陆异常
     */
    public final static String FAIL_UNLOGIN = "999993";
}
