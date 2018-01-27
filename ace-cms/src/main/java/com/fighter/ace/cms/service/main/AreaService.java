package com.fighter.ace.cms.service.main;


import com.fighter.ace.cms.entity.main.Area;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;

import java.util.List;
import java.util.Map;

/**
 * Created by kehwa on 2016/8/6.
 */
public interface AreaService {

    int updateArea(Area area);

    PageBean getListPage(PageParam pageParam, Map<String, Object> paramMap);

    List findAreas(Map<String, Object> paramMap);

    Area getById(Long id);
}
