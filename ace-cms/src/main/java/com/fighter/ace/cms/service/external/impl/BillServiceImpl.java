package com.fighter.ace.cms.service.external.impl;

import com.fighter.ace.cms.dao.external.CostsDao;
import com.fighter.ace.cms.service.external.BillService;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by hanebert on 18/8/28.
 */
@Service(value = "billService")
public class BillServiceImpl implements BillService{

    @Resource
    private CostsDao costsDao;

    @Override
    public PageBean getListPage(PageParam pageParam, Map<String, Object> paramMap) {
        return costsDao.listPage(pageParam, paramMap);
    }

}
