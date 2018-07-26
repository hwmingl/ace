package com.fighter.ace.cms.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by hanebert on 18/7/17.
 */
public class MsgUtil {

    private static final String USERID = "2545";
    private static final String ACCOUNT = "3dmaking";
    private static final String PASSWORD = "surface1A";
    private static final String ACTION = "send";
    private static final String SMS_SEND_URI = "http://sms.kingtto.com:9999/sms.aspx?";

    public static String sendMessage(String telphone, String content) throws UnsupportedEncodingException {
        String urlEncode = java.net.URLEncoder.encode("【增材制造】" + content, "UTF-8");
        String httpArg = "action="+ACTION+"&userid="+USERID+"&account="+ACCOUNT+"&password="+PASSWORD+"&mobile="+telphone+"&content="+urlEncode;
        String httpUrl = SMS_SEND_URI+httpArg;
        return sendRequest(httpUrl);

    }

    private static String sendRequest(String requestUrl){
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}
