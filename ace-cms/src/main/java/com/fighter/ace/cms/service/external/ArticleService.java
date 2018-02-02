package com.fighter.ace.cms.service.external;

import com.fighter.ace.cms.entity.external.Article;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;

import java.util.List;
import java.util.Map;

/**
 * Created by hanebert on 18/1/28.
 */
public interface ArticleService {


    Long addArticle(Article article);

    List<Article> findHotList(Integer count);

    List<Article> findRecommendList(Integer count);

    PageBean getListPage(PageParam pageParam, Map<String, Object> paramMap);

    int deleteArticle(int id);

    int batchDeleteArticle(int[] ids);

    Article getById(Long id);

    int updateArticle(Article article);

    int checkArticle(int[] ids);

}
