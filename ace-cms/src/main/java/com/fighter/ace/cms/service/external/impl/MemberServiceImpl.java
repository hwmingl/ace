package com.fighter.ace.cms.service.external.impl;

import com.fighter.ace.cms.dao.external.CostsDao;
import com.fighter.ace.cms.dao.external.MemberDao;
import com.fighter.ace.cms.dao.external.ModelDownloadDao;
import com.fighter.ace.cms.entity.external.Costs;
import com.fighter.ace.cms.entity.external.Member;
import com.fighter.ace.cms.entity.external.ModelDownload;
import com.fighter.ace.cms.enums.CostSrcEnum;
import com.fighter.ace.cms.enums.CostTypeEnum;
import com.fighter.ace.cms.service.external.MemberService;
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
 * Created by hanebert on 18/1/31.
 */
@Service(value = "memberService")
public class MemberServiceImpl implements MemberService {

    private static final Logger log = LoggerFactory.getLogger(MemberServiceImpl.class);

    @Autowired
    private MemberDao memberDao;
    @Autowired
    private CostsDao costsDao;
    @Autowired
    private ModelDownloadDao modelDownloadDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addMember(Member member) {
        Long id = 0L;
        try {
            member.setCity("-1");
            member.setSex(0);
            member.setPoint("200");
            id = memberDao.insert(member);

            //添加积分来源记录
            Costs points = new Costs();
            points.setMemberId(id);
            points.setType(CostTypeEnum.POINTS.getCode());
            points.setCurrent("0");
            points.setMark("200");
            Double finalized = 200.00;
            points.setFinalized(String.valueOf(finalized));
            points.setRemark(CostSrcEnum.REGISTER.getDesc());
            points.setSource(CostSrcEnum.REGISTER.getCode());
            costsDao.insert(points);
        } catch (Exception e){
            log.error("addMember error", e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Member addCost(Member member, Long modelId, int payType, Float cost) {

        try {
            //修改用户积分或金币数据
            Member updMember = new Member();
            updMember.setId(member.getId());
            //添加积分来源记录
            Costs points = new Costs();
            points.setMemberId(member.getId());

            //扣除积分
            if (payType == 1){
                points.setType(CostTypeEnum.POINTS.getCode());
                points.setCurrent(member.getPoint());
                points.setMark(String.valueOf(0 - cost.intValue()));
                int finalized = Integer.valueOf(member.getPoint()) - cost.intValue();
                points.setFinalized(String.valueOf(finalized));

                member.setPoint(points.getFinalized());
                updMember.setPoint(points.getFinalized());


            }
            //扣除金币
            if (payType == 2){
                points.setType(CostTypeEnum.COINS.getCode());
                points.setCurrent(member.getCoin());
                points.setMark(String.valueOf(0 - cost.intValue()));
                int finalized = Integer.valueOf(member.getCoin()) - cost.intValue();
                points.setFinalized(String.valueOf(finalized));

                member.setCoin(points.getFinalized());
                updMember.setCoin(points.getFinalized());
            }
            points.setType(payType);
            points.setRemark(CostSrcEnum.DOWNLOAD.getDesc());
            points.setSource(CostSrcEnum.DOWNLOAD.getCode());
            costsDao.insert(points);
            //--------------------------------------------------------------------------
            memberDao.update(updMember);


            //下载记录
            ModelDownload modelDownload = new ModelDownload();
            modelDownload.setMemberId(member.getId());
            modelDownload.setModelId(modelId);
            modelDownload.setCost(cost.intValue());
            modelDownload.setPayType(1);
            modelDownloadDao.insert(modelDownload);
        } catch (Exception e){
            log.error("addCost error",e);
        }
        return member;
    }

    @Override
    public PageBean getListPage(PageParam pageParam, Map<String, Object> paramMap) {
        return memberDao.listPage(pageParam,paramMap);
    }

    @Override
    public Member getById(Long id) {
        return memberDao.getById(id);
    }

    @Override
    public Member getByPhone(String phone) {
        return memberDao.getByPhone(phone);
    }

    @Override
    public Member findByPhoneAndUserName(String account, String password) {
        return memberDao.findByPhoneAndUserName(account,password);
    }

    @Override
    public int updateStatusById(Integer id) {
        return 0;
    }

    @Override
    public int updateBatchById(int[] id) {
        return 0;
    }

    @Override
    public int update(Member member) {
        return memberDao.update(member);
    }

    @Override
    public int deleteById(Integer id) {
        return 0;
    }
}
