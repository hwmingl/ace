package com.fighter.ace.cms.service.external.impl;

import com.fighter.ace.cms.dao.external.ModelDownloadDao;
import com.fighter.ace.cms.entity.external.ModelDownload;
import com.fighter.ace.cms.service.external.DownloadService;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by hanebert on 18/9/1.
 */
@Service(value = "downloadService")
public class DownloadServiceImpl implements DownloadService {

    @Resource
    private ModelDownloadDao modelDownloadDao;

    @Override
    public PageBean getListPage(PageParam pageParam, Map<String, Object> paramMap) {
        return modelDownloadDao.listPage(pageParam,paramMap);
    }

    @Override
    public boolean hasDownload(Long memberId, Long modelId) {
        ModelDownload modelDownload = modelDownloadDao.getModelDownload(memberId, modelId);
        if (null != modelDownload){
            return true;
        }
        return false;
    }


}
