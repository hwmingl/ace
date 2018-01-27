package com.fighter.ace.cms.service.external;


import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;

import java.util.Date;

/**
 * Created by hanebert on 16/6/28.
 */
public interface ActivationService {

    void save(Date expiredTime, Integer num, Integer point);

    int deleteById(long id);

    int deleteByIds(long[] ids);

    PageBean findList(PageParam pageParam, String code);

}
