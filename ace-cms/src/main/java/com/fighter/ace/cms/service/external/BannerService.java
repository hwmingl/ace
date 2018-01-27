package com.fighter.ace.cms.service.external;


import com.fighter.ace.cms.entity.external.Banner;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;

import java.util.Map;

/**
 * Created by hanebert on 16/8/6.
 */
public interface BannerService {

    PageBean ListPage(PageParam pageParam, Map<String, Object> paramMap);

    int update(Banner banner);

    Banner getById(Long id);

}
