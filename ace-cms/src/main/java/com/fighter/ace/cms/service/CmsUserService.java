package com.fighter.ace.cms.service;

import com.fighter.ace.cms.entity.main.CmsUser;
import com.fighter.ace.framework.common.exceptions.BizException;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;

import java.util.Map;

/**
 * Created by hanebert on 17/9/22.
 */
public interface CmsUserService {

    CmsUser getByUserName(String userName) throws BizException;

    Long save(CmsUser cmsUser) throws BizException;

    PageBean getListPage(PageParam pageParam, Map<String, Object> paramMap) throws BizException;

}
