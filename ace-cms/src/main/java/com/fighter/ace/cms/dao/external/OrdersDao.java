package com.fighter.ace.cms.dao.external;


import com.fighter.ace.cms.entity.external.Orders;
import com.fighter.ace.framework.common.dao.BaseDaoImpl;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hanebert on 16/7/23.
 */
@Repository("ordersDao")
public class OrdersDao extends BaseDaoImpl<Orders> {
    public PageBean ListPage(PageParam pageParam, Map<String, Object> paramMap) {
        if(paramMap == null) {
            paramMap = new HashMap();
        }
        ((Map)paramMap).put("pageFirst", Integer.valueOf((pageParam.getPageNum() - 1) * pageParam.getNumPerPage()));
        ((Map)paramMap).put("pageSize", Integer.valueOf(pageParam.getNumPerPage()));
        Long count = (Long)getSessionTemplate().selectOne(this.getStatement("listPageCount"), paramMap);
        List list = getSessionTemplate().selectList(this.getStatement("listPage"), paramMap);
        return new PageBean(pageParam.getPageNum(), pageParam.getNumPerPage(), count.intValue(), list);
    }


    public Orders getByOrderNo(String orderNo){
        Map<String,String> map = new HashMap<>();
        map.put("orderNo","'"+orderNo+"'");
        return getSessionTemplate().selectOne(getStatement("getByOrderNo"),map);
    }

}
