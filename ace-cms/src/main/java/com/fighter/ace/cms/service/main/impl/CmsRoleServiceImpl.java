package com.fighter.ace.cms.service.main.impl;

import com.fighter.ace.cms.dao.main.CmsRoleDao;
import com.fighter.ace.cms.entity.main.CmsRole;
import com.fighter.ace.cms.service.main.CmsRoleService;
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
    public List<CmsRole> findAll() throws BizException {
        PageParam pageParam = new PageParam(1,20);
        PageBean<CmsRole> pageBean = getListPage(pageParam, null);
        if (null != pageBean && pageBean.getTotalCount() > 0){
            return pageBean.getRecordList();
        }
        return new ArrayList<>();
    }

    @Override
    public Long save(CmsRole cmsRole) throws BizException {
        return cmsRoleDao.insert(cmsRole);
    }

    @Override
    public CmsRole getById(Long id) throws BizException {
        return cmsRoleDao.getById(id);
    }

    @Override
    public CmsRole getByName(String name) throws BizException {
        Map<String,Object> params = Maps.newHashMap();
        params.put("name",name);
        return cmsRoleDao.getBy(params);
    }

    @Override
    public int delete(Long id) throws BizException {
        return cmsRoleDao.deleteById(id);
    }


}
