package com.fighter.ace.cms.dao.external;


import com.fighter.ace.cms.entity.external.Favourite;
import com.fighter.ace.framework.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hanebert on 16/5/26.
 */
@Repository("favouriteDao")
public class FavouriteDao extends BaseDaoImpl<Favourite> {

    public Favourite findBy(Long memberId,Long modelId){
        Map map = new HashMap();
        map.put("memberId",memberId);
        map.put("modelId", modelId);
        return getSessionTemplate().selectOne("findByMemberIdAndModelId", map);
    }

    public int deleteFavourite(int[] ids){
        return getSessionTemplate().delete("batchDeleteFavourite", ids);
    }
}
