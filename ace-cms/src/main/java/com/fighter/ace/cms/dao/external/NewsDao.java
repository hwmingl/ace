package com.fighter.ace.cms.dao.external;


import com.fighter.ace.cms.entity.external.News;
import com.fighter.ace.framework.common.dao.BaseDaoImpl;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by hanebert on 16/5/21.
 */
@Repository("newsDao")
public class NewsDao extends BaseDaoImpl<News> {
    public static final String SQL_BATCH_DELETE = "batchDelete";
    public static final String SQL_BATCH_CHECK = "batchCheck";

    public int batchDeleteNews(int[] ids) {
        return getSessionTemplate().delete(getStatement(SQL_BATCH_DELETE), ids);
    }

    public int checkNews(int[] ids) {
        return getSessionTemplate().delete(getStatement(SQL_BATCH_CHECK), ids);
    }

    public List<News> findRecommendList(Integer count){
        return getSessionTemplate().selectList(getStatement("findRecommendList"), count);
    }
    public List<News> findListByStyle(Map<String,Object> paramMap){
        return getSessionTemplate().selectList(getStatement("findListByStyle"), paramMap);
    }

    public List<News> findHotList(Integer count){
        return getSessionTemplate().selectList(getStatement("findHotList"),count);
    }
}
