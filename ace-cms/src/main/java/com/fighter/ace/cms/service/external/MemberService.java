package com.fighter.ace.cms.service.external;


import com.fighter.ace.cms.entity.external.Member;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;

import java.util.Map;

/**
 * Created by kehwa on 2016/5/28.
 */
public interface MemberService {

    /**
     *  新增会员
     * @param member
     */
    void addMember(Member member);


    /**
     *
     * @param member
     * @param modelId
     * @param payType
     * @param cost
     */
    Member addCost(Member member,Long modelId,int payType,Float cost);

    /**
     *
     * @param pageParam
     * @param paramMap
     * @return
     */
    PageBean getListPage(PageParam pageParam, Map<String, Object> paramMap);

    /**
     *
     * @param id
     * @return
     */
    Member getById(Long id);

    /**
     *
     * @param phone
     * @return
     */
    Member getByPhone(String phone);


    /**
     *
     * @param account
     * @param password
     * @return
     */
    Member findByPhoneAndUserName(String account,String password);

    /**
     *
     * @param id
     * @return
     */
    int updateStatusById(Integer id);

    /**
     *
     * @param id
     * @return
     */
    int updateBatchById(int[] id);

    /**
     *
     * @param member
     * @return
     */
    int update(Member member);

    /**
     *
     * @param id
     * @return
     */
    int deleteById(Integer id);

}