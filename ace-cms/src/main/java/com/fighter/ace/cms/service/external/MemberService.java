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
     *
     * @param member
     */
    void addMember(Member member);

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