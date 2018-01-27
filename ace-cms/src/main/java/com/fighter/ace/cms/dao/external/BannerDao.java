package com.fighter.ace.cms.dao.external;

import com.fighter.ace.cms.entity.external.Banner;
import com.fighter.ace.framework.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hanebert on 16/8/6.
 */
@Repository("bannerDao")
public class BannerDao extends BaseDaoImpl<Banner> {

    public Banner getByTitle(String title){
        Map<String,Object> map = new HashMap<>();
        map.put("title","'"+title+"'");
        return getSessionTemplate().selectOne(getStatement("getByTitle"), map);
    }

}
