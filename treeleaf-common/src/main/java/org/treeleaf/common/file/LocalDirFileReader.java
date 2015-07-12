package org.treeleaf.common.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * 本地磁盘文件读取者
 * <p/>
 * Created by yaoshuhong on 2015/4/22.
 */
public class LocalDirFileReader implements FileReader {

    private File file;

    public LocalDirFileReader(String path) {
        this.file = new File(path);
    }

    @Override
    public boolean exist() {
        return this.file.exists();
    }

    @Override
    public InputStream getInputStream() {
        try {
            return new FileInputStream(this.file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getFileName() {
        return this.file.getName();
    }
}
