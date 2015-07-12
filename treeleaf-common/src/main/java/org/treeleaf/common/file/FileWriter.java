package org.treeleaf.common.file;

import java.io.OutputStream;

/**
 * Created by yaoshuhong on 2015/5/25.
 */
public interface FileWriter {

    /**
     * 判断文件是否存在
     *
     * @return 文件是否存在
     */
    boolean exist();

    /**
     * 获取文件的的输入流
     *
     * @return 文件输入流
     */
    OutputStream getOutputStream();

    /**
     * 获取文件名称
     *
     * @return 文件名称
     */
    String getFileName();


}
