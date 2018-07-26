package com.fighter.ace.cms.service.external.impl;

import com.fighter.ace.cms.dao.external.MemberDao;
import com.fighter.ace.cms.entity.external.Member;
import com.fighter.ace.cms.service.external.MemberService;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by hanebert on 18/1/31.
 */
@Service(value = "memberService")
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    @Override
    public void addMember(Member member) {
        memberDao.insert(member);
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
