package com.fighter.ace.cms.dao.external;


import com.fighter.ace.cms.entity.external.Cash;
import com.fighter.ace.cms.entity.external.CashMember;
import com.fighter.ace.framework.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by hanebert on 16/7/25.
 */
@Repository("cashDao")
public class CashDao extends BaseDaoImpl<Cash> {

    public CashMember getCashMemberById(Long id){
        return getSessionTemplate().selectOne(getStatement("getCashMemberById"),id);
    }

}
