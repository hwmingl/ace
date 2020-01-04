package com.fighter.ace.cms.util;


import com.fighter.ace.cms.entity.main.MarkConfig;
import com.fighter.ace.cms.entity.main.CmsUser;
import org.apache.commons.lang.RandomStringUtils;

import static com.fighter.ace.cms.util.Constants.UPLOAD_PATH;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by hanebert on 16/5/30.
 */
public class CmsUtil {


    public static final String RMB_TO_COINS = "RMB_TO_COINS";
    public static final String RECHARGE_RMB_LEVEL = "RECHARGE_RMB_LEVEL";
    public static final String RECHARGE_LARGESS_POINT = "RECHARGE_LARGESS_POINT";
    public static final String DOWNLOAD_LARGESS_POINT = "DOWNLOAD_LARGESS_POINT";
    public static final String DOWNLOAD_DIVIDE = "DOWNLOAD_DIVIDE";

    /**
     * 用户KEY
     */
    public static final String ADMIN_USER_KEY = "_admin_user_key";

    /**
     * 获得上传路径。如：/u/jeecms/path
     *
     * @return
     */
    public static String getUploadPath() {
        return UPLOAD_PATH;
    }

    public static MarkConfig getMarkConfig(){
        MarkConfig markConfig = new MarkConfig();
        markConfig.setOn(false);

        return markConfig;
    }


    public static String getFullId(Long id){
        String tmp = String.valueOf(id);
        int length = tmp.length();
        if (length < 5){
            for (int i = 0; i < (5-length); i++) {
                tmp = "0"+tmp;
            }
        }
        return tmp;
    }

    public static String getTradeNo(Long id){
        String mId = getFullId(id);
        StringBuffer sb = new StringBuffer();
        sb.append("M").append(mId);
        String timeStr = String.valueOf(System.currentTimeMillis());
        sb.append("T").append(timeStr);
        sb.append(RandomStringUtils.randomAlphanumeric(6));
        return sb.toString().toUpperCase();
    }

    /**
     * 获得用户ID
     *
     * @param request
     * @return
     */
    public static Long getAdminUserId(HttpServletRequest request) {
        CmsUser user = getAdminUser(request);
        if (user != null) {
            return Long.valueOf(user.getId());
        } else {
            return null;
        }
    }

    /**
     * 获得用户
     *
     * @param request
     * @return
     */
    public static CmsUser getAdminUser(HttpServletRequest request) {
        return (CmsUser) request.getSession().getAttribute(ADMIN_USER_KEY);
    }

    public static String getCode() {
        int code = (int) ((Math.random() * 9 + 1) * 100000);
        return String.valueOf(code);
    }


    public static void main(String[] args){

        int x = 0;
        for(int j = 0; j<=6; j++){
            if (j%2 == 0){
                continue;
            }

            x += j;

        }

        System.out.println(x);
    }




}
