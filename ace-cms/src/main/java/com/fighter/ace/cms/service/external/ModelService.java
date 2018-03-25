package com.fighter.ace.cms.service.external;


import com.fighter.ace.cms.entity.external.Model;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;

/**
 * Created by hanebert on 16/6/4.
 */
public interface ModelService {

    /**
     *
     * @param model
     */
    void createModel(Model model);

    /**
     * 根据条件查询分页
     * @param pageParam     pageNo & pageSize
     * @param name          模型名字
     * @param categoryId    类别ID
     * @param memberName    会员昵称
     * @return
     */
    PageBean findList(PageParam pageParam, String name, Long categoryId, String memberName, Integer queryOrderBy,Integer status);

    /**
     *
     * @param id
     * @return
     */
    Model getById(Long id);

    /**
     *
     * @param model
     */
    void update(Model model);

    /**
     *
     * @param id
     */
    void deleteById(Long id);

    /**
     *
     * @param ids
     */
    void deleteByIds(Long[] ids);

    /**
     *
     * @param ids
     */
    void approveByIds(Long[] ids);



}
