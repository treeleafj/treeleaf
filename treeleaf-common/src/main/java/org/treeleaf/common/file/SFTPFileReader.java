package org.treeleaf.common.file;

import java.io.InputStream;

/**
 * SFTP读取器
 * <p/>
 * 暂不实现
 * <p/>
 * Created by yaoshuhong on 2015/4/22.
 */
public class SFTPFileReader implements FileReader {

    @Override
    public boolean exist() {
        return false;
    }

    @Override
    public InputStream getInputStream() {
        return null;
    }

    @Override
    public String getFileName() {
        return null;
    }
}
