package com.fighter.ace.cms.service.main.impl;


import com.fighter.ace.cms.dao.main.DictDao;
import com.fighter.ace.cms.entity.main.Dict;
import com.fighter.ace.cms.service.main.DictService;
import com.fighter.ace.framework.common.exceptions.BizException;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by hanebert on 16/7/24.
 */
@Service(value = "dictService")
public class DictServiceImpl implements DictService {

    private static final Logger log = LoggerFactory.getLogger(DictServiceImpl.class);
    @Autowired
    private DictDao dictDao;


    @Override
    public PageBean getListPage(PageParam pageParam, Map<String, Object> paramMap) {
        PageBean pageBean;
        try{
            pageBean = dictDao.listPage(pageParam,paramMap);
        }catch (Exception e){
            log.error("getListPage error",e);
            throw new BizException("getListPage error",e);
        }
        return pageBean;
    }

    @Override
    public Dict getById(Long id) {
        Dict dict = null;
        try{
            dict = dictDao.getById(id);
        }catch (Exception e){
            log.error("getById error",e);
            throw new BizException("getById error",e);
        }
        return dict;
    }

    @Override
    public int update(Dict dict) {
        int k = 0;
        try{
            k = dictDao.update(dict);
        }catch (Exception e){
            log.error("update error",e);
            throw new BizException("update error",e);
        }
        return k;
    }

    @Override
    public Dict getByName(String name) {
        Dict dict = null;
        try {
            dict = dictDao.getByName(name);
        }catch (Exception e){
            log.error("getByName error",e);
            throw new BizException("getByName error",e);
        }
        return dict;
    }
}
