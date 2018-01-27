package com.fighter.ace.cms.dao.external;


import com.fighter.ace.cms.entity.external.News;
import com.fighter.ace.framework.common.dao.BaseDaoImpl;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hanebert on 16/5/21.
 */
@Repository("newsDao")
public class NewsDao extends BaseDaoImpl<News> {
    public static final String SQL_BATCH_DELETE = "batchDelete";
    public static final String SQL_BATCH_CHECK = "batchCheck";

    @Autowired
    SqlSessionTemplate sessionTemplate;
    SqlSession sqlSession;

    @Override
    public SqlSessionTemplate getSessionTemplate() {
        return sessionTemplate;
    }

    @Override
    public void setSessionTemplate(SqlSessionTemplate sessionTemplate) {
        this.sessionTemplate = super.getSessionTemplate();
    }

    @Override
    public SqlSession getSqlSession() {
        return sqlSession;
    }

    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = super.getSqlSession();
    }

    public int batchDeleteNews(int[] ids) {
        return sessionTemplate.delete(getStatement(SQL_BATCH_DELETE), ids);
    }

    public int checkNews(int[] ids) {
        return sessionTemplate.delete(getStatement(SQL_BATCH_CHECK), ids);
    }

    public List<News> findRecommendList(Integer count){
        return getSessionTemplate().selectList("findRecommendList", count);
    }

    public List<News> findHotList(Integer count){
        return getSessionTemplate().selectList("com.jiutian.cms.making.dao.NewsDao.findHotList",count);
    }
}
