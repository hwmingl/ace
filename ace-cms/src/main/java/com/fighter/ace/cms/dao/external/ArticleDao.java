package com.fighter.ace.cms.dao.external;

import com.fighter.ace.cms.entity.external.Article;
import com.fighter.ace.framework.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hanebert on 18/1/23.
 */
@Repository("articleDao")
public class ArticleDao extends BaseDaoImpl<Article> {

    public static final String SQL_BATCH_DELETE = "batchDelete";
    public static final String SQL_BATCH_CHECK = "batchCheck";

    public int batchDelete(int[] ids) {
        return getSessionTemplate().delete(getStatement(SQL_BATCH_DELETE), ids);
    }

    public int check(int[] ids) {
        return getSessionTemplate().delete(getStatement(SQL_BATCH_CHECK), ids);
    }


    public List<Article> findHotList(Integer count){
        return getSessionTemplate().selectList(getStatement("findHotList"), count);
    }

    public List<Article> findRecommendList(Integer count){
        return getSessionTemplate().selectList(getStatement("findRecommendList"), count);
    }

}
