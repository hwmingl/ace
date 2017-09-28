package com.fighter.ace.cms.util;


import com.fighter.ace.cms.entity.main.MarkConfig;
import com.fighter.ace.cms.entity.main.CmsUser;

import static com.fighter.ace.cms.util.Constants.UPLOAD_PATH;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by hanebert on 16/5/30.
 */
public class CmsUtil {

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

}
