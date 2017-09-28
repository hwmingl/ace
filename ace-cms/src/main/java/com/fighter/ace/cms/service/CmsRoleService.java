package com.fighter.ace.cms.service;

import com.fighter.ace.cms.entity.main.CmsRole;
import com.fighter.ace.framework.common.exceptions.BizException;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;

import java.util.Map;

/**
 * Created by hanebert on 17/9/25.
 */
public interface CmsRoleService {


    PageBean getListPage(PageParam pageParam, Map<String, Object> paramMap) throws BizException;


    Long save(CmsRole cmsRole) throws BizException;

    CmsRole getByName(String name) throws BizException;
}
