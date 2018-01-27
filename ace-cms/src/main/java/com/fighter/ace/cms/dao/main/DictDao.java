package com.fighter.ace.cms.dao.main;

import com.fighter.ace.cms.entity.main.Dict;
import com.fighter.ace.framework.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hanebert on 18/1/13.
 */
@Repository("dictDao")
public class DictDao extends BaseDaoImpl<Dict> {

    public Dict getByName(String name){
        Map<String,Object> map = new HashMap<>();
        map.put("name","'"+name+"'");
        return getSessionTemplate().selectOne(getStatement("getByName"),map);
    }

    public Map<String,String> getDictMap(){
        List<Dict> dicts = getSessionTemplate().selectList(getStatement("findDictList"));
        Map<String,String> map = new HashMap<>();
        for (Dict dict:dicts){
            map.put(dict.getName(),dict.getData());
        }
        return map;
    }

}
