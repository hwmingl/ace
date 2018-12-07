package com.fighter.ace.cms.service.external.impl;


import com.fighter.ace.cms.dao.external.CashDao;
import com.fighter.ace.cms.dao.external.CoinsDao;
import com.fighter.ace.cms.dao.external.MemberDao;
import com.fighter.ace.cms.entity.external.Cash;
import com.fighter.ace.cms.entity.external.Coins;
import com.fighter.ace.cms.entity.external.Member;
import com.fighter.ace.cms.enums.CostSrcEnum;
import com.fighter.ace.cms.service.external.CashService;
import com.fighter.ace.framework.common.exceptions.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by hanebert on 16/7/25.
 */
@Service(value = "cashService")
public class CashServiceImpl implements CashService {

    private static final Logger log = LoggerFactory.getLogger(CashServiceImpl.class);

    @Autowired
    private CashDao cashDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private CoinsDao coinsDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Long createCash(Cash cash) {
        Long id = 0L;
        try{
            id = cashDao.insert(cash);

            Member m = memberDao.getById(cash.getMemberId());
            Member member = new Member();
            member.setId(cash.getMemberId());
            Double result = Double.valueOf(m.getCoin()) - Double.valueOf(cash.getCoins());
            member.setCoin(String.valueOf(result));
            memberDao.update(member);

            //添加消费金币记录
            Coins coins = new Coins();
            coins.setMemberId(cash.getMemberId());
            coins.setCurrent(String.valueOf(m.getCoin()));
            coins.setMark(String.valueOf(0 - Double.valueOf(cash.getCoins())));
            coins.setFinalized(String.valueOf(result));
            coins.setRemark(String.valueOf(id));
            coins.setSource(CostSrcEnum.CASH.getCode());
            coinsDao.insert(coins);

        }catch (Exception e){
            e.printStackTrace();
            log.error("createCash error," + e.getMessage(), e);
            throw new BizException("createCash error,"+e.getMessage());
        }
        return id;
    }
}
