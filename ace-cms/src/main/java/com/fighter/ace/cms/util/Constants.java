package com.fighter.ace.cms.util;

/**
 * Created by hanebert on 16/5/28.
 */
public class Constants {

    /**
     * 上传路径
     */
    public static final String UPLOAD_PATH = "/u/cms";

    /**
     * 资源路径
     */
    public static final String RES_PATH = "/r/cms";
    /**
     * 素材库远程同步地址
     */
    public static final String LIB_UPLOAD_URL = "http://219.232.255.207:8080/3dlib/frontcms/common/o_upload_file.do";

    //****************************************常见状态************************************************
    //逻辑删除
    public static final Integer STATUS_DELETED = -1;
    //待审
    public static final Integer STATUS_WAIT = 0;
    //已审
    public static final Integer STATUS_OK = 1;
    //打回
    public static final Integer STATUS_BACK = 403;


}
