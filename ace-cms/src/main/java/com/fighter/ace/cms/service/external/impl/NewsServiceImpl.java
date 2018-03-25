package com.fighter.ace.cms.service.external.impl;


import com.fighter.ace.cms.dao.external.NewsDao;
import com.fighter.ace.cms.entity.external.News;
import com.fighter.ace.cms.service.external.NewsService;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hanebert on 16/5/28.
 */
@Service(value = "newsService")
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsDao newsDao;

    @Override
    public Long addNews(News news) {
        long newsId = newsDao.insert(news);
        return newsId;
    }

    @Override
    public List<News> findHotList(Integer count) {
        return newsDao.findHotList(count);
    }

    @Override
    public List<News> findListByStyle(Integer style, Integer count) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("style",style);
        paramMap.put("count",count);
        return newsDao.findListByStyle(paramMap);
    }

    @Override
    public List<News> findRecommendList(Integer count) {
        return newsDao.findRecommendList(count);
    }

    @Override
    public PageBean getListPage(PageParam pageParam, Map<String, Object> paramMap) {
        return newsDao.listPage(pageParam, paramMap);
    }

    @Override
    public int deleteNews(int id) {
        return newsDao.deleteById(id);
    }

    @Override
    public int batchDeleteNews(int[] ids) {
       return newsDao.batchDeleteNews(ids);
    }

    @Override
    public News getById(Long id) {
        return newsDao.getById(id);
    }

    @Override
    public int updateNews(News news) {
        return newsDao.update(news);
    }

    @Override
    public int checkNews(int[] ids) {
        return newsDao.checkNews(ids);
    }
}
