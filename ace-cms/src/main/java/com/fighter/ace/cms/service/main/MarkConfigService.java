package com.fighter.ace.cms.service.main;


import com.fighter.ace.cms.entity.main.MarkConfig;

/**
 * Created by hanebert on 16/6/2.
 */
public interface MarkConfigService {
    /**
     * 新增
     * @param markConfig
     */
    void create(MarkConfig markConfig);

    /**
     * 修改
     * @param markConfig
     */
    void update(MarkConfig markConfig);

    /**
     * 根据ID获取水印配置
     * @param id
     * @return
     */
    MarkConfig getById(Long id);

    /**
     * 数据库水印配置
     * @return
     */
    MarkConfig getMarkConfig();

}
