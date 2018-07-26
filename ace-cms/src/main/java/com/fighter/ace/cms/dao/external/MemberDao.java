package com.fighter.ace.cms.dao.external;

import com.fighter.ace.cms.entity.external.Member;
import com.fighter.ace.framework.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by hanebert on 16/5/26.
 */
@Repository("memberDao")
public class MemberDao extends BaseDaoImpl<Member> {


    public int updateStatusById(Integer id){
        return getSessionTemplate().update(getStatement("deleteMember"), Long.valueOf(id));
    }
    public int updateBatchById(int[] ids){
        return getSessionTemplate().update(getStatement("deleteBatch"), ids);
    }

    public Member findMember(String username){
        return getSessionTemplate().selectOne(getStatement("findMemberByUserName"), username);
    }

    public Member getByPhone(String phone){
        return getSessionTemplate().selectOne(getStatement("getByPhone"), phone);
    }

    public Member findByEmail(String email){
        return getSessionTemplate().selectOne(getStatement("findByEmail"), email);
    }


}
