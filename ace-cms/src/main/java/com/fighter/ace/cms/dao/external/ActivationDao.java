package com.fighter.ace.cms.dao.external;


import com.fighter.ace.cms.entity.external.Activation;
import com.fighter.ace.framework.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by hanebert on 16/6/27.
 */
@Repository("activationDao")
public class ActivationDao extends BaseDaoImpl<Activation> {

    public int deleteById(Long id){
        return getSessionTemplate().update(getStatement("deleteById"),id);
    }

    public int batchDelete(long[] ids){
        return getSessionTemplate().update(getStatement("batchDelete"),ids);
    }

    public Activation findByCode(String code){
        return getSessionTemplate().selectOne(getStatement("findActCode"),code);
    }

}
