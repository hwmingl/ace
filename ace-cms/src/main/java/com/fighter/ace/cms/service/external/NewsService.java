package com.fighter.ace.cms.service.external;


import com.fighter.ace.cms.entity.external.News;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;

import java.util.Map;

/**
 * Created by hanebert on 16/5/28.
 */
public interface NewsService {

    Long addNews(News news);

    PageBean getListPage(PageParam pageParam, Map<String, Object> paramMap);

    int deleteNews(int id);

    int batchDeleteNews(int[] ids);

    News getById(int id);

    int updateNews(News news);

    int checkNews(int[] ids);
}
