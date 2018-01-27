package com.fighter.ace.framework.common.remote;

import java.io.File;

/**
 * Created by hanebert on 16/6/21.
 */
public class FileBean implements java.io.Serializable {

    private File file;
    private String destDir;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getDestDir() {
        return destDir;
    }

    public void setDestDir(String destDir) {
        this.destDir = destDir;
    }
}
