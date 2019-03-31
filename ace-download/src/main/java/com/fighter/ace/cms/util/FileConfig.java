package com.fighter.ace.cms.util;

/**
 * Created by hanebert on 18/9/2.
 */
public class FileConfig implements java.io.Serializable{

    /**
     * 图片根路径
     */
    private String imgRootPath;
    /**
     * 下载前缀
     */
    private String pathPrefix;

    public String getPathPrefix() {
        return pathPrefix;
    }

    public void setPathPrefix(String pathPrefix) {
        this.pathPrefix = pathPrefix;
    }

    public String getImgRootPath() {
        return imgRootPath;
    }

    public void setImgRootPath(String imgRootPath) {
        this.imgRootPath = imgRootPath;
    }
}
