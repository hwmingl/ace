package com.fighter.ace.cms.dao.external;

import com.fighter.ace.cms.entity.external.Category;
import com.fighter.ace.framework.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hanebert on 16/5/26.
 */
@Repository("categoryDao")
public class CategoryDao extends BaseDaoImpl<Category> {

    public List<Category> findListByParentId(Long parentId){
        return getSessionTemplate().selectList(getStatement("selectListByParentId"),parentId);
    }

    public List<Category> findListByName(Long parentId,String name){
        Map map = new HashMap();
        map.put("name",name);
        map.put("parentId", parentId);
        return getSessionTemplate().selectList(getStatement("selectListByName"), map);
    }

    public int batchDelete(Long[] ids){
        return getSessionTemplate().update(getStatement("deleteByBatch"),ids);
    }

}
