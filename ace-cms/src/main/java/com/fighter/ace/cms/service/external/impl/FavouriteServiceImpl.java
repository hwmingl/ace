package com.fighter.ace.cms.service.external.impl;

import com.fighter.ace.cms.dao.external.FavouriteDao;
import com.fighter.ace.cms.entity.external.Favourite;
import com.fighter.ace.cms.service.external.FavouriteService;
import com.fighter.ace.framework.common.exceptions.BizException;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by hanebert on 18/8/29.
 */
@Service(value = "favouriteService")
public class FavouriteServiceImpl implements FavouriteService {

    private static final Logger log = LoggerFactory.getLogger(FavouriteServiceImpl.class);

    @Autowired
    private FavouriteDao favouriteDao;
    @Override
    public void addFavourite(Long modelId, Long memberId) {
        try {
            Favourite favourite = new Favourite();
            favourite.setModelId(modelId);
            favourite.setMemberId(memberId);
            favouriteDao.insert(favourite);
        }catch (Exception e){
            e.printStackTrace();
            log.error("addFavourite error," + e.getMessage(), e);
            throw new BizException("addFavourite error,"+e.getMessage());
        }
    }

    @Override
    public boolean hasFavourite(Long modelId, Long memberId) {
        boolean hasFav = false;
        try {
            Favourite v = favouriteDao.findBy(memberId,modelId);
            if(v != null){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("hasFavourite error," + e.getMessage(), e);
            throw new BizException("hasFavourite error,"+e.getMessage());
        }
        return hasFav;
    }

    @Override
    public PageBean findList(PageParam pageParam, Map<String, Object> map) {
        try {
            PageBean pageBean =  favouriteDao.listPage(pageParam, map);
            return pageBean;
        }catch (Exception e){
            e.printStackTrace();
            log.error("find favourite List error,"+e.getMessage(),e);
            throw new BizException("find favourite List error,"+e.getMessage());
        }
    }

    @Override
    public int deleteFavourite(int[] ids) {
        try {
            return favouriteDao.deleteFavourite(ids);
        }catch (Exception e){
            e.printStackTrace();
            log.error("delete favourite error,"+e.getMessage(),e);
            throw new BizException("delete favourite error,"+e.getMessage());
        }
    }

}
