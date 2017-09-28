package com.fighter.ace.cms.service.impl;

import com.fighter.ace.cms.dao.main.CmsRoleDao;
import com.fighter.ace.cms.entity.main.CmsRole;
import com.fighter.ace.cms.service.CmsRoleService;
import com.fighter.ace.framework.common.exceptions.BizException;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by hanebert on 17/9/25.
 */
@Service(value = "cmsRoleService")
public class CmsRoleServiceImpl implements CmsRoleService {

    @Autowired
    private CmsRoleDao cmsRoleDao;


    @Override
    public PageBean getListPage(PageParam pageParam, Map<String, Object> paramMap) throws BizException{
        return cmsRoleDao.listPage(pageParam,paramMap);
    }

    @Override
    public Long save(CmsRole cmsRole) throws BizException {
        return cmsRoleDao.insert(cmsRole);
    }

    @Override
    public CmsRole getByName(String name) throws BizException {
        Map<String,Object> params = Maps.newHashMap();
        params.put("name",name);
        return cmsRoleDao.getBy(params);
    }


}
