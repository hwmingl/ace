package com.fighter.ace.cms.dao.external;


import com.fighter.ace.cms.entity.external.Notice;
import com.fighter.ace.framework.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hanebert on 16/5/26.
 */
@Repository("noticeDao")
public class NoticeDao extends BaseDaoImpl<Notice> {

    public List<Notice> findNoticeList(int count){
        return getSessionTemplate().selectList("findNoticeList",Integer.valueOf(count));
    }

    public int batchDelete(int[] id){
        return getSessionTemplate().update(getStatement("deleteByBatch"),id);
    }

}
