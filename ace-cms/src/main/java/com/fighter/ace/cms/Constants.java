package com.fighter.ace.cms;

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
