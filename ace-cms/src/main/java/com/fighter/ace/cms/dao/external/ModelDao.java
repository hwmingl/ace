package com.fighter.ace.cms.dao.external;


import com.fighter.ace.cms.entity.external.Model;
import com.fighter.ace.cms.entity.external.ModelMemCate;
import com.fighter.ace.framework.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by hanebert on 16/5/26.
 */
@Repository("modelDao")
public class ModelDao extends BaseDaoImpl<Model> {

    public ModelMemCate getMmcById(Long id){
        return getSessionTemplate().selectOne(getStatement("getMmcById"),id);
    }

    public int batchDelete(Long[] ids){
        return getSessionTemplate().update(getStatement("batchDelete"), ids);
    }

    public int approveByIds(Long[] ids){
        return getSessionTemplate().update(getStatement("approveByBatch"), ids);
    }

    public Long getTotalCount(Map<String, Object> paramMap){
        return getSessionTemplate().selectOne(getStatement("listPageCount"),paramMap);
    }
}
