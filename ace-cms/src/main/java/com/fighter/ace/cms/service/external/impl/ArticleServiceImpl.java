package com.fighter.ace.cms.service.external.impl;

import com.fighter.ace.cms.dao.external.ArticleDao;
import com.fighter.ace.cms.entity.external.Article;
import com.fighter.ace.cms.service.external.ArticleService;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by hanebert on 18/1/28.
 */
@Service(value = "articleService")
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Override
    public Long addArticle(Article article) {
        return articleDao.insert(article);
    }

    @Override
    public List<Article> findHotList(Integer count) {
        return articleDao.findHotList(count);
    }

    @Override
    public List<Article> findRecommendList(Integer count) {
        return articleDao.findRecommendList(count);
    }


    @Override
    public PageBean getListPage(PageParam pageParam, Map<String, Object> paramMap) {
        return articleDao.listPage(pageParam,paramMap);
    }

    @Override
    public int deleteArticle(int id) {
        return articleDao.deleteById(id);
    }

    @Override
    public int batchDeleteArticle(int[] ids) {
        return articleDao.batchDelete(ids);
    }

    @Override
    public Article getById(Long id) {
        return articleDao.getById(id);
    }

    @Override
    public int updateArticle(Article article) {
        return articleDao.update(article);
    }

    @Override
    public int checkArticle(int[] ids) {
        return articleDao.check(ids);
    }
}
