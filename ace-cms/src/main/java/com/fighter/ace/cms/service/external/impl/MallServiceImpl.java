package com.fighter.ace.cms.service.external.impl;

import com.fighter.ace.cms.dao.external.MallDao;
import com.fighter.ace.cms.entity.external.Mall;
import com.fighter.ace.cms.service.external.MallService;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by hanebert on 18/2/2.
 */
@Service(value = "mallService")
public class MallServiceImpl implements MallService{

    @Autowired
    private MallDao mallDao;

    @Override
    public PageBean getListPage(PageParam pageParam, Map paramMap) {
        return mallDao.listPage(pageParam,paramMap);
    }

    @Override
    public Mall getById(Long id) {
        return mallDao.getById(id);
    }

    @Override
    public Long createMall(Mall mall) {
        return mallDao.insert(mall);
    }

    @Override
    public int updateMall(Mall mall) {
        return mallDao.update(mall);
    }

    @Override
    public void deleteById(Long id) {
        mallDao.deleteById(id);
    }

    @Override
    public void deleteBatch(Long[] ids) {
        mallDao.deleteBatch(ids);
    }

    @Override
    public void approveBatch(Long[] ids) {
        mallDao.approveBatch(ids);
    }
}
