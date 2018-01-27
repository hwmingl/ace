package com.fighter.ace.cms.service.main.impl;

import com.fighter.ace.cms.dao.main.CmsUserDao;
import com.fighter.ace.cms.entity.main.CmsUser;
import com.fighter.ace.cms.service.main.CmsUserService;
import com.fighter.ace.framework.common.exceptions.BizException;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hanebert on 17/9/22.
 */
@Service(value = "cmsUserService")
public class CmsUserServiceImpl implements CmsUserService {

    @Autowired
    private CmsUserDao cmsUserDao;

    @Override
    public CmsUser getById(Long id) throws BizException {
        return cmsUserDao.getById(id);
    }

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
    public int update(CmsUser cmsUser) throws BizException {
        return cmsUserDao.update(cmsUser);
    }

    @Override
    public List<CmsUser> findAll() throws BizException {
        PageParam pageParam = new PageParam(1,20);
        PageBean<CmsUser> pageBean = getListPage(pageParam, null);
        if (null != pageBean && pageBean.getTotalCount() > 0){
            return pageBean.getRecordList();
        }
        return new ArrayList<>();
    }

    @Override
    public List<CmsUser> findListByRoleId(Long roleId) throws BizException {
        Map<String,Object> params = Maps.newHashMap();
        params.put("role", roleId.toString());
        return cmsUserDao.listBy(params);
    }

    @Override
    public PageBean getListPage(PageParam pageParam, Map<String, Object> paramMap) throws BizException {
        return cmsUserDao.listPage(pageParam,paramMap);
    }
}
