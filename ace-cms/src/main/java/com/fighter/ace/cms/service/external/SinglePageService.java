package com.fighter.ace.cms.service.external;

import com.fighter.ace.cms.entity.external.SinglePage;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;


import java.util.Map;

/**
 * Created by kehwa on 2016/6/4.
 */
public interface SinglePageService {

    Long createSinglePage(SinglePage singlePage);

    void updateSinglePage(SinglePage singlePage);

    SinglePage getById(Long id);

    PageBean getListPage(PageParam pageParam, Map<String, Object> paramMap);
}
