package com.fighter.ace.cms.service.external.impl;


import com.fighter.ace.cms.dao.external.CoinsDao;
import com.fighter.ace.cms.dao.external.MemberDao;
import com.fighter.ace.cms.dao.external.OrdersDao;
import com.fighter.ace.cms.dao.external.PointsDao;
import com.fighter.ace.cms.dao.main.DictDao;
import com.fighter.ace.cms.entity.external.Coins;
import com.fighter.ace.cms.entity.external.Member;
import com.fighter.ace.cms.entity.external.Orders;
import com.fighter.ace.cms.entity.external.Points;
import com.fighter.ace.cms.enums.CostSrcEnum;
import com.fighter.ace.cms.service.external.OrdersFacadeService;
import com.fighter.ace.cms.util.CmsUtil;
import com.fighter.ace.framework.common.exceptions.BizException;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * Created by hanebert on 16/7/23.
 */
@Service(value = "ordersFacadeService")
public class OrdersFacadeServiceImpl implements OrdersFacadeService {

    private static final Logger log = LoggerFactory.getLogger(OrdersFacadeServiceImpl.class);

    @Autowired
    private OrdersDao ordersDao;
    @Autowired
    private PointsDao pointsDao;
    @Autowired
    private DictDao dictDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private CoinsDao coinsDao;

    public Long saveOrders(Orders orders){
        Long id = 0l;
        try{
           id = ordersDao.insert(orders);
        }catch (Exception e){
            log.error("saveOrders error,"+e.getMessage(),e);
            throw new BizException("saveOrders error,"+e.getMessage());
        }
        return id;
    }

    @Override
    public int updateById(Orders orders) {
        try {
            return ordersDao.update(orders);
        } catch (Exception e){
            log.error("updateById error",e);
        }
        return 0;
    }

    @Override
    public int updateOnlyTradeNo(Long orderId, String tradeNo) {
        int n = 0;
        try{
            //更新订单状态,支付完成
            Orders ord = new Orders();
            ord.setId(orderId);
            ord.setTradeNo(tradeNo);
            ord.setStatus(1);//完成支付
            n = ordersDao.update(ord);
        }catch (Exception e){
            log.error("updateOnlyTradeNo error",e);
        }
        return n;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int updateTradeNo(Long orderId,String tradeNo){
        int n = 0;
        try{
            Orders orders = ordersDao.getById(orderId);
            int totalFee = Integer.parseInt(orders.getTotalFee());
            Map<String,String> dictMap = dictDao.getDictMap();
            Member member = memberDao.getById(orders.getMemberId());
            //针对会员的操作
            Member m = new Member();
            m.setId(orders.getMemberId());
            //大于充值线,可送积分
            String level = dictMap.get("RECHARGE_RMB_LEVEL");
            if (totalFee >= Integer.parseInt(dictMap.get(CmsUtil.RECHARGE_RMB_LEVEL))){
                //添加积分来源记录
                Points points = new Points();
                points.setMemberId(orders.getMemberId());
                points.setSource(CostSrcEnum.RECHARGE.getCode());
                points.setCurrent(member.getPoint());
                int ratio = Integer.parseInt(dictMap.get(CmsUtil.RECHARGE_LARGESS_POINT));
                Double mark = Double.valueOf(totalFee * ratio)/100 ;
                points.setMark(String.valueOf(mark));
                Double finalized = Double.valueOf(member.getPoint()) + mark;
                points.setFinalized(String.valueOf(finalized));
                points.setRemark(orders.getOrderNo());
                pointsDao.insert(points);
                //更新会员积分
                m.setPoint(String.valueOf(finalized.intValue()));
            }
            //金币入库
            Coins coins = new Coins();
            coins.setMemberId(orders.getMemberId());
            coins.setSource(CostSrcEnum.RECHARGE.getCode());
            coins.setCurrent(String.valueOf(member.getCoin()));
            int ratio = Integer.parseInt(dictMap.get(CmsUtil.RMB_TO_COINS));
            Double mark = Double.valueOf(totalFee * ratio);
            coins.setMark(String.valueOf(mark));
            Double finalized = Double.valueOf(member.getCoin()) + mark;
            coins.setFinalized(String.valueOf(finalized));
            coins.setRemark(orders.getOrderNo());
            coinsDao.insert(coins);
            //更新会员积分和金币
            m.setCoin(String.valueOf(finalized));
            memberDao.update(m);
            //更新订单状态,支付完成
            Orders ord = new Orders();
            ord.setId(orderId);
            ord.setTradeNo(tradeNo);
            ord.setStatus(1);//完成支付
            n = ordersDao.update(ord);

        }catch (Exception e){
            log.error("updateTradeNo error,"+e.getMessage(),e);
            throw new BizException("updateTradeNo error,"+e.getMessage());
        }
        return n;
    }

    @Override
    public Orders getByOrderNo(String orderNo) {
        try{
            Orders orders = ordersDao.getByOrderNo(orderNo);
            return orders;
        }catch (Exception e){
            log.error("getByOrderNo error,"+e.getMessage(),e);
            throw new BizException("getByOrderNo error,"+e.getMessage());
        }
    }

    @Override
    public Orders getById(Long id) {
        return ordersDao.getById(id);
    }

    @Override
    public PageBean getListPage(PageParam pageParam, Map<String, Object> paramMap) {
        return ordersDao.listPage(pageParam,paramMap);
    }
}
