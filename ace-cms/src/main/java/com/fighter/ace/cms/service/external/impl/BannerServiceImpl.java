package com.fighter.ace.cms.service.external.impl;

import com.fighter.ace.cms.dao.external.BannerDao;
import com.fighter.ace.cms.entity.external.Banner;
import com.fighter.ace.cms.service.external.BannerService;
import com.fighter.ace.framework.common.exceptions.BizException;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by hanebert on 16/8/6.
 */
@Service(value = "bannerService")
public class BannerServiceImpl implements BannerService {

    private static final Logger log = LoggerFactory.getLogger(BannerServiceImpl.class);

    @Autowired
    private BannerDao bannerDao;

    @Override
    public PageBean ListPage(PageParam pageParam, Map<String, Object> paramMap) {
        PageBean pageBean = null;
        try{
            pageBean = bannerDao.listPage(pageParam,paramMap);
        }catch (Exception e){
            e.printStackTrace();
            log.error("banner ListPage error",e);
        }
        return pageBean;
    }

    @Override
    public int update(Banner banner) {
        if (null == banner || null == banner.getId()){
            throw new BizException("banner or id is null");
        }
        int k = 0;
        try{
            k = bannerDao.update(banner);
        }catch (Exception e){
            e.printStackTrace();
            log.error("banner update error", e);
        }
        return k;
    }

    @Override
    public Banner getById(Long id) {
        Banner banner = null;
        try{
            banner = bannerDao.getById(id);
        }catch (Exception e){
            e.printStackTrace();
            log.error("banner getById error", e);
        }
        return banner;
    }
}
