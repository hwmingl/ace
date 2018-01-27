package com.fighter.ace.cms.dao.external;

import com.fighter.ace.cms.entity.external.Article;
import com.fighter.ace.framework.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by hanebert on 18/1/23.
 */
@Repository("articleDao")
public class ArticleDao extends BaseDaoImpl<Article> {

    public static final String SQL_BATCH_DELETE = "batchDelete";
    public static final String SQL_BATCH_CHECK = "batchCheck";



}
