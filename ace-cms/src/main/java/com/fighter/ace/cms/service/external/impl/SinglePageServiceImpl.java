package com.fighter.ace.cms.service.external.impl;


import com.fighter.ace.cms.dao.external.SinglePageDao;
import com.fighter.ace.cms.entity.external.SinglePage;
import com.fighter.ace.cms.service.external.SinglePageService;
import com.fighter.ace.framework.common.exceptions.BizException;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by kehwa on 2016/6/4.
 */
@Service(value = "singlePageService")
public class SinglePageServiceImpl implements SinglePageService {
    private static final Logger log = LoggerFactory.getLogger(SinglePageServiceImpl.class);

    @Autowired
    private SinglePageDao singlePageDao;

    @Override
    public Long createSinglePage(SinglePage singlePage) {

        try {
            Long id = singlePageDao.insert(singlePage);
            log.info("create SinglePage title={}", singlePage.getTitle());
            return id;
        }catch (Exception e){
            e.printStackTrace();
            log.error("createSinglePage error,"+e.getMessage(),e);
            throw new BizException("createSinglePage error ,"+e.getMessage());
        }
    }

    @Override
    public void updateSinglePage(SinglePage singlePage) {
        try {
            singlePageDao.update(singlePage);
        }catch (Exception e){
            e.printStackTrace();
            log.error("updateSinglePage error",e);
            throw new BizException("updateSinglePage error ,"+e.getMessage());
        }
    }

    @Override
    public PageBean getListPage(PageParam pageParam, Map<String, Object> paramMap) {
        PageBean pageBean;
        try {
            pageBean = singlePageDao.listPage(pageParam,paramMap);
        }catch (Exception e){
            e.printStackTrace();
            log.error("singlePageService getListPage",e);
            throw new BizException("singlePageService error",e);
        }
        return pageBean;
    }

    @Override
    public SinglePage getById(Long id) {
        try {
            return singlePageDao.getById(id);
        }catch (Exception e){
            e.printStackTrace();
            log.error("getById error,"+e.getMessage(),e);
            throw new BizException("getById error",e);
        }
    }

}
