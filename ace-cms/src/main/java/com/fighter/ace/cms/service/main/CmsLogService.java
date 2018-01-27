package com.fighter.ace.cms.service.main;

import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by hanebert on 17/9/28.
 */
public interface CmsLogService {

    void record(HttpServletRequest request, String title, String content);


    PageBean getListPage(PageParam pageParam, Map<String, Object> paramMap);

}
