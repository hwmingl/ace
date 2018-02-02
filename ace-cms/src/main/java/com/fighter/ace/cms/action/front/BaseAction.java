package com.fighter.ace.cms.action.front;

import org.apache.commons.lang.StringUtils;

/**
 * Created by hanebert on 18/1/20.
 */
public class BaseAction {


    protected Integer getPageNo(String pageNo) {
        if (StringUtils.isBlank(pageNo)){
            return 1;
        }
        return Integer.valueOf(pageNo);
    }

}
