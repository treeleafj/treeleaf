package org.treeleaf.common.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by yaoshuhong on 2015/5/25.
 */
public class LocalDirFileWriter implements FileWriter {

    private File file;

    public LocalDirFileWriter(String path) {
        this.file = new File(path);
    }

    @Override
    public boolean exist() {
        return this.file.exists();
    }

    @Override
    public OutputStream getOutputStream() {
        try {
            if (!this.file.exists()) {
                this.file.createNewFile();
            }
            return new FileOutputStream(file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getFileName() {
        return this.file.getName();
    }
}
