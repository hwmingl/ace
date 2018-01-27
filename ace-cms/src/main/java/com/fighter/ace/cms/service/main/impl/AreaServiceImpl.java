package com.fighter.ace.cms.service.main.impl;

import com.fighter.ace.cms.dao.main.AreaDao;
import com.fighter.ace.cms.entity.main.Area;
import com.fighter.ace.cms.service.main.AreaService;
import com.fighter.ace.framework.common.exceptions.BizException;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by kehwa on 2016/8/6.
 */
@Service("areaService")
public class AreaServiceImpl implements AreaService {
    private static final Logger log = LoggerFactory.getLogger(AreaService.class);

    @Autowired
    private AreaDao areaDao;
    @Override
    public int updateArea(Area area) {
        try {
            return areaDao.update(area);
        }catch (Exception e){
            e.printStackTrace();
            log.error("updateArea error", e.getMessage());
            throw new BizException("updateArea error", e);
        }
    }

    @Override
    public PageBean getListPage(PageParam pageParam, Map<String, Object> paramMap) {
        PageBean pageBean;
        try{
            pageBean = areaDao.listPage(pageParam,paramMap);
        }catch (Exception e){
            log.error("getListPage error",e);
            throw new BizException("getListPage error",e);
        }
        return pageBean;
    }

    @Override
    public List findAreas(Map<String, Object> paramMap) {
        try {
            return areaDao.findAreas(paramMap);
        }catch (Exception e){
            e.printStackTrace();
            log.error("findAreas error", e.getMessage());
            throw new BizException("findAreas error", e);
        }
    }

    @Override
    public Area getById(Long id) {
        return areaDao.getById(id);
    }
}
