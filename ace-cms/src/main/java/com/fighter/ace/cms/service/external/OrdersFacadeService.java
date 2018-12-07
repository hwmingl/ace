package com.fighter.ace.cms.service.external;


import com.fighter.ace.cms.entity.external.Orders;

/**
 * Created by hanebert on 16/7/23.
 */
public interface OrdersFacadeService {

    Long saveOrders(Orders orders);

    int updateTradeNo(Long orderId, String tradeNo);

    Orders getByOrderNo(String orderNo);

}
