package com.fighter.ace.cms.service.external;

import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;

import java.util.Map;

/**
 * Created by hanebert on 18/8/28.
 */
public interface BillService {


    /**
     * 分页查询消费记录
     * @param pageParam
     * @param paramMap
     * @return
     */
    PageBean getListPage(PageParam pageParam, Map<String, Object> paramMap);

}
