package com.fighter.ace.code.util;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by hanebert on 17/10/31.
 */
public class JavaPrimitiveTypeMapping {

    static Map<String, String> wraper2primitive = new HashMap();
    static Map<String, String> primitive2wraper = new HashMap();

    public JavaPrimitiveTypeMapping() {
    }

    public static String getPrimitiveType(String clazz) {
        String className = StringHelper.getExtension(clazz);
        System.out.println(className);
        String result = (String)wraper2primitive.get(className);
        return result == null?clazz:result;
    }

    public static String getWrapperType(String clazz) {
        String result = (String)primitive2wraper.get(clazz);
        return result == null?clazz:result;
    }

    static {
        wraper2primitive.put("Byte", "byte");
        wraper2primitive.put("Short", "short");
        wraper2primitive.put("Integer", "int");
        wraper2primitive.put("Long", "long");
        wraper2primitive.put("Float", "float");
        wraper2primitive.put("Double", "double");
        wraper2primitive.put("Boolean", "boolean");
        wraper2primitive.put("Integer", "int");
        wraper2primitive.put("Character", "char");
        Iterator i$ = wraper2primitive.keySet().iterator();

        while(i$.hasNext()) {
            String key = (String)i$.next();
            primitive2wraper.put(wraper2primitive.get(key), key);
        }

    }

}
