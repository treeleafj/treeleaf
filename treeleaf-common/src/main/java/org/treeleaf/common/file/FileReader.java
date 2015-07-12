package org.treeleaf.common.file;

import java.io.InputStream;

/**
 * 文件读取者,用于抽象文件流的获取
 * <p/>
 * 文件可能是从本地磁盘文件,也有可能是从远程SFTP下载
 * <p/>
 * Created by yaoshuhong on 2015/4/22.
 */
public interface FileReader {

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
    InputStream getInputStream();

    /**
     * 获取文件名称
     * @return 文件名称
     */
    String getFileName();
}
