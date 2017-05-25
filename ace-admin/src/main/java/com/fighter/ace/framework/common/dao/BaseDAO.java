package com.fighter.ace.framework.common.dao;

/**
 * Created by hanebert on 17/5/25.
 */
public interface BaseDAO<T> {

    /**
     * 根据实体对象插入新增记录
     * @param entity
     * @return
     */
    Long insert(T entity);

    /**
     * 更新对象
     * @param entity
     * @return
     */
    int update(T entity);

    /**
     * 根据ID获取对象
     * @param id
     * @return
     */
    T getById(Long id);



}
