package com.fighter.ace.cms.util;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by hanebert on 18/7/18.
 */
public class JsonUtil {

    public static String toJson(String key,Object value){
        JSONObject data = new JSONObject();
        data.put(key,value);
        return data.toJSONString();
    }

}
