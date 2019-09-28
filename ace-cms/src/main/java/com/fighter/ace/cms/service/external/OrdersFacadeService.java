package com.fighter.ace.cms.service.external;


import com.fighter.ace.cms.entity.external.Orders;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;

import java.util.Map;

/**
 * Created by hanebert on 16/7/23.
 */
public interface OrdersFacadeService {

    Long saveOrders(Orders orders);

    int updateById(Orders orders);

    int updateOnlyTradeNo(Long orderId, String tradeNo);

    int updateTradeNo(Long orderId, String tradeNo);

    Orders getByOrderNo(String orderNo);

    Orders getById(Long id);

    PageBean getListPage(PageParam pageParam, Map<String, Object> paramMap);

}
