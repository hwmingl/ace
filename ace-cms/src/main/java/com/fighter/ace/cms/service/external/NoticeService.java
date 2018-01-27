package com.fighter.ace.cms.service.external;



import com.fighter.ace.cms.entity.external.Notice;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;

import java.util.Map;

/**
 * Created by hanebert on 16/5/28.
 */
public interface NoticeService {

    long addNotice(Notice notice);

    PageBean getListPage(PageParam pageParam, Map<String, Object> paramMap);

    int deleteNotice(int id);

    int batchDeleteNotice(int[] ids);

    Notice getById(int id);

    int updateNotice(Notice notice);

    int checkNotice(int[] ids);


}
