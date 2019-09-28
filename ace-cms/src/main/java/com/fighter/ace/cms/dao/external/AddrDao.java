package com.fighter.ace.cms.dao.external;

import com.fighter.ace.cms.entity.external.Addr;
import com.fighter.ace.framework.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by hanebert on 2019/3/7.
 */
@Repository("addrDao")
public class AddrDao extends BaseDaoImpl<Addr> {

    public Addr getByDeft(){
       return getSessionTemplate().selectOne(getStatement("getByDeft"));
    }

}
