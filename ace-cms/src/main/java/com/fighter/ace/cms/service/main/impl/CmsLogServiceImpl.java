package com.fighter.ace.cms.service.main.impl;

import com.fighter.ace.cms.dao.main.CmsLogDao;
import com.fighter.ace.cms.entity.main.CmsLog;
import com.fighter.ace.cms.service.main.CmsLogService;
import com.fighter.ace.cms.util.CmsUtil;
import com.fighter.ace.framework.common.exceptions.BizException;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;
import com.fighter.ace.framework.web.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by hanebert on 17/9/28.
 */
@Service(value = "cmsLogService")
public class CmsLogServiceImpl implements CmsLogService {

    private static final Logger log = LoggerFactory.getLogger(CmsLogServiceImpl.class);
    @Autowired
    private CmsLogDao cmsLogDao;

    @Override
    public void record(HttpServletRequest request, String title, String content) {
        try{
            CmsLog cmsLog = new CmsLog();
            cmsLog.setUserId(CmsUtil.getAdminUserId(request));
            cmsLog.setIp(RequestUtils.getIpAddr(request));
            cmsLog.setTitle(title);
            cmsLog.setContent(content);
            cmsLogDao.insert(cmsLog);
            log.info("save cmsLog id={}",cmsLog.getId());
        }catch (Exception e){
            log.error("operating error",e);
            throw new BizException("operating error",e);
        }
    }

    @Override
    public PageBean getListPage(PageParam pageParam, Map<String, Object> paramMap) {
        return cmsLogDao.listPage(pageParam,paramMap);
    }
}
