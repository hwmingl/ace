package com.fighter.ace.cms.dao.main;

import com.fighter.ace.cms.entity.main.Area;
import com.fighter.ace.framework.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kehwa on 2016/8/6.
 */
@Repository("areaDao")
public class AreaDao extends BaseDaoImpl<Area> {


    public int updateArea(Area area){
        return this.update(area);
    }


    public List findAreas(Map<String,Object> paramMap){
        if(paramMap==null){
            paramMap = new HashMap<>();
        }
        List list = null;
        list = getSessionTemplate().selectList(this.getStatement("listArea"), paramMap);
        return  list;
    }

}
