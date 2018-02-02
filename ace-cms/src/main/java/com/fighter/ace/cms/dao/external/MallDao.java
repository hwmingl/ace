package com.fighter.ace.cms.dao.external;

import com.fighter.ace.cms.entity.external.Mall;
import com.fighter.ace.framework.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by hanebert on 18/2/2.
 */
@Repository("mallDao")
public class MallDao extends BaseDaoImpl<Mall> {

    public int deleteBatch(Long[] ids){
        return getSessionTemplate().update(getStatement("deleteBatch"),ids);
    }

    public int approveBatch(Long[] ids){
        return getSessionTemplate().update(getStatement("approveBatch"),ids);
    }

}
