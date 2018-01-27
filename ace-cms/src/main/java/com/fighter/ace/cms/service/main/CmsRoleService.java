package com.fighter.ace.cms.service.main;

import com.fighter.ace.cms.entity.main.CmsRole;
import com.fighter.ace.framework.common.exceptions.BizException;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;

import java.util.List;
import java.util.Map;

/**
 * Created by hanebert on 17/9/25.
 */
public interface CmsRoleService {


    PageBean getListPage(PageParam pageParam, Map<String, Object> paramMap) throws BizException;

    List<CmsRole> findAll() throws BizException;

    Long save(CmsRole cmsRole) throws BizException;

    CmsRole getById(Long id) throws BizException;

    CmsRole getByName(String name) throws BizException;

    int delete(Long id) throws BizException;

}
