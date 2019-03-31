package com.fighter.ace.cms.service.external;

import com.fighter.ace.cms.entity.external.Addr;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;

import java.util.Map;

/**
 * Created by hanebert on 2019/3/7.
 */
public interface AddrService {

    /**
     * 新增
     * @param addr
     * @return
     */
    Long addAddr(Addr addr);

    /**
     * 分页地址管理
     * @param pageParam
     * @param paramMap
     * @return
     */
    PageBean getListPage(PageParam pageParam, Map<String, Object> paramMap);

    /**
     * 删除地址
     * @param id
     * @return
     */
    int deleteAddr(Long id);

    /**
     * 获取地址
     * @param id
     * @return
     */
    Addr getById(Long id);

    /**
     * 更新地址
     * @param addr
     * @return
     */
    int updateAddr(Addr addr);


}
