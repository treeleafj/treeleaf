package org.treeleaf.common.template;

import java.util.Map;

/**
 * 模版工具解析工具抽象类
 *
 * @Author leaf
 * 2015/10/8 0008 21:13.
 */
public abstract class Templater {

    /**
     * 解析模版
     *
     * @param param 变量参数
     * @return
     */
    public abstract String parse(Map<String, Object> param);

}
