package com.fighter.ace.framework.common.util;

/**
 * Created by hanebert on 16/7/3.
 */
public class StringUtil {


    public static String getDestDir(String fileName){
        return fileName.substring(0,fileName.lastIndexOf("/")+1);
    }

    public static void main(String[] args){
        System.out.println(getDestDir("/u/cms/201606/d.stl"));
    }

}
