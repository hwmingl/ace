package com.fighter.ace.cms.dao.external;

import com.fighter.ace.cms.entity.external.ModelDownload;
import com.fighter.ace.framework.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hanebert on 18/9/1.
 */
@Repository("modelDownloadDao")
public class ModelDownloadDao extends BaseDaoImpl<ModelDownload> {

    public int deleteModelDownload(int[] ids){
        return getSessionTemplate().delete("deleteModelDownload", ids);
    }

    public ModelDownload getModelDownload(Long memberId,Long modelId){
        Map<String,Long> map = new HashMap();
        map.put("memberId",memberId);
        map.put("modelId",modelId);
        return getSessionTemplate().selectOne(getStatement("getModelDownload"),map);
    }

}
