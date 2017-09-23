package com.fighter.ace.framework.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by hxxing on 2016/5/10.
 */
public class MD5Util {
    /**
     * MD5加密生成32位的md5码
     * @param inStr
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String md5Encode(String inStr) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }

        byte[] byteArray = new byte[0];
        try {
            byteArray = inStr.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for(int i=0; i<md5Bytes.length; i++){
            int val = (md5Bytes[i]) & 0xff;
            if(val <16){
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString().toUpperCase();
    }

    public static void main(String[] args) {
        System.out.println(md5Encode("password"));
    }
}
