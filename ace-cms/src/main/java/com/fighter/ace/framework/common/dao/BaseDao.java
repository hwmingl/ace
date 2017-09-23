package com.fighter.ace.framework.common.dao;

import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;
import java.util.Map;

/**
 * Created by hanebert on 16/4/24.
 */
public interface BaseDao<T> {

    /**
     * 根据实体对象新增记录.
     *
     * @param entity
     *
     * @return id
     */
    long insert(T entity);

    /**
     * 批量保存对象
     * @param list
     * @return
     */
    long insert(List<T> list);

    /**
     * 更新实体对应的记录.
     *
     * @param entity
     *            .
     * @return
     */
    int update(T entity);

    /**
     * 批量更新对象
     * @param list
     * @return
     */
    int update(List<T> list);

    /**
     * 根据ID查找记录.
     *
     * @param id
     * @return entity
     */
    T getById(long id);

    /**
     * 根据ID删除记录.
     *
     * @param id
     * @return
     */
    int deleteById(long id);

    /**
     * 分页查询 .
     *
     * @param pageParam
     *            分页参数.
     * @param paramMap
     *            业务条件查询参数.
     * @return
     */
    public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap);

    /**
     * 根据条件查询 listBy: <br/>
     *
     * @param paramMap
     * @return 返回集合
     */
    public List<T> listBy(Map<String, Object> paramMap);

    /**
     * 根据条件查询 listBy: <br/>
     *
     * @param paramMap
     * @return 返回实体
     */
    public T getBy(Map<String, Object> paramMap);

    public SqlSessionTemplate getSessionTemplate();

    public SqlSession getSqlSession();
}
