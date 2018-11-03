package com.fighter.ace.cms.service.external;

import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;

import java.util.Map;

/**
 * Created by hanebert on 18/9/1.
 */
public interface DownloadService {


    /**
     * 分页查询下载记录
     * @param pageParam
     * @param paramMap
     * @return
     */
    PageBean getListPage(PageParam pageParam, Map<String, Object> paramMap);

    /**
     * 是否已经下载过
     * @param memberId
     * @param modelId
     * @return
     */
    boolean hasDownload(Long memberId,Long modelId);

}
