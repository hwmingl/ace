package com.fighter.ace.cms.service.external;

import com.fighter.ace.cms.entity.external.Mall;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;

import java.util.Map;

/**
 * Created by hanebert on 18/2/2.
 */
public interface MallService {

    /**
     *
     * @param pageParam
     * @param paramMap
     * @return
     */
    PageBean getListPage(PageParam pageParam,Map paramMap);


    /**
     *
     * @param id
     * @return
     */
    Mall getById(Long id);

    /**
     *
     * @param mall
     * @return
     */
    Long createMall(Mall mall);

    /**
     *
     * @param mall
     * @return
     */
    int updateMall(Mall mall);

    /**
     *
     * @param id
     */
    void deleteById(Long id);

    /**
     *
     * @param ids
     */
    void deleteBatch(Long[] ids);

    /**
     *
     * @param ids
     */
    void approveBatch(Long[] ids);



}
