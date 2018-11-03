package com.fighter.test.service;

import com.fighter.ace.cms.entity.external.Member;
import com.fighter.ace.cms.service.external.MemberService;
import com.fighter.test.BaseJunit4Test;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by hanebert on 18/8/26.
 */
public class MemberServiceTest extends BaseJunit4Test{

    @Resource
    private MemberService memberService;

    @Test
    public void addMemberTest(){

        Member member = new Member();
        member.setPhone("15129705658");
        member.setUserPwd("111111");
        member.setUserName("15129705658");

        memberService.addMember(member);
        Assert.assertTrue(true);
    }



}
