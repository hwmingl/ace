package com.fighter.ace.cms.service.main.impl;

import com.fighter.ace.cms.dao.main.MarkConfigDao;
import com.fighter.ace.cms.entity.main.MarkConfig;
import com.fighter.ace.cms.service.main.MarkConfigService;
import com.fighter.ace.framework.common.exceptions.BizException;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by hanebert on 16/6/2.
 */
@Service(value = "markConfigService")
public class MarkConfigServiceImpl implements MarkConfigService {

    private static final Logger log = LoggerFactory.getLogger(MarkConfigServiceImpl.class);

    private static final long MARK_CONFIG_ID = 1L;

    @Autowired
    private MarkConfigDao markConfigDao;

    @Override
    public void create(MarkConfig markConfig) {
        Preconditions.checkNotNull(markConfig, "markConfigDto is null");
        try{
            markConfigDao.insert(markConfig);
            log.info("create markConfig id={}", markConfig.getId());
        }catch (Exception e){
            e.printStackTrace();
            log.error("create error",e);
            throw new BizException("create error",e);
        }
    }

    @Override
    public void update(MarkConfig markConfig) {
        Preconditions.checkNotNull(markConfig, "markConfigDto is null");
        Preconditions.checkArgument(markConfig.getId() != 0, "id is invalid");
        try{
            markConfigDao.update(markConfig);
            log.info("update markConfig id={}", markConfig.getId());
        }catch (Exception e){
            e.printStackTrace();
            log.error("update error",e);
            throw new BizException("update error",e);
        }
    }

    @Override
    public MarkConfig getById(Long id) {
        Preconditions.checkArgument(id != 0, "id is invalid");
        MarkConfig markConfig = null;
        try{
            markConfig = markConfigDao.getById(id);
        }catch (Exception e){
            e.printStackTrace();
            log.error("getById error",e);
            throw new BizException("getById error",e);
        }
        return markConfig;
    }

    @Override
    public MarkConfig getMarkConfig() {
        MarkConfig markConfig = null;
        try{
            markConfig = markConfigDao.getById(MARK_CONFIG_ID);
        }catch (Exception e){
            e.printStackTrace();
            log.error("getMarkConfig error",e);
            throw new BizException("getMarkConfig error",e);
        }
        return markConfig;
    }
}
