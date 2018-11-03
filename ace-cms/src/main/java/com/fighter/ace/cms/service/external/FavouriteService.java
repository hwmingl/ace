package com.fighter.ace.cms.service.external;

import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;

import java.util.Map;

/**
 * Created by hanebert on 18/8/29.
 */
public interface FavouriteService {

    void addFavourite(Long modelId, Long memberId);

    boolean hasFavourite(Long modelId, Long memberId);

    PageBean findList(PageParam pageParam, Map<String, Object> map);

    int deleteFavourite(int[] ids);

}
