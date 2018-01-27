package com.fighter.ace.cms.service.main;



import com.fighter.ace.cms.entity.main.Dict;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;

import java.util.Map;

/**
 * Created by hanebert on 16/7/24.
 */
public interface DictService {

    public static final String RMB_TO_COINS = "RMB_TO_COINS";
    public static final String RECHARGE_RMB_LEVEL = "RECHARGE_RMB_LEVEL";
    public static final String RECHARGE_LARGESS_POINT = "RECHARGE_LARGESS_POINT";
    public static final String DOWNLOAD_LARGESS_POINT = "DOWNLOAD_LARGESS_POINT";
    public static final String DOWNLOAD_DIVIDE = "DOWNLOAD_DIVIDE";

    PageBean getListPage(PageParam pageParam, Map<String, Object> paramMap);

    Dict getById(Long id);

    int update(Dict dict);

    Dict getByName(String name);

}
