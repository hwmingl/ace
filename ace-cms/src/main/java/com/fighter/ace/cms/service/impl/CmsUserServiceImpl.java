package com.fighter.ace.cms.service.impl;

import com.fighter.ace.cms.dao.main.CmsUserDao;
import com.fighter.ace.cms.entity.main.CmsUser;
import com.fighter.ace.cms.service.CmsUserService;
import com.fighter.ace.framework.common.exceptions.BizException;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by hanebert on 17/9/22.
 */
@Service(value = "cmsUserService")
public class CmsUserServiceImpl implements CmsUserService {

    @Autowired
    private CmsUserDao cmsUserDao;

    @Override
    public CmsUser getByUserName(String userName) throws BizException {
        Map<String,Object> params = Maps.newHashMap();
        params.put("username",userName);
        return cmsUserDao.getBy(params);
    }

    @Override
    public Long save(CmsUser cmsUser) throws BizException {
        return cmsUserDao.insert(cmsUser);
    }

    @Override
    public PageBean getListPage(PageParam pageParam, Map<String, Object> paramMap) throws BizException {
        return cmsUserDao.listPage(pageParam,paramMap);
    }
}
