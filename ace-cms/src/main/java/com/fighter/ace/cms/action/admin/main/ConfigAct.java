package com.fighter.ace.cms.action.admin.main;

import com.fighter.ace.cms.entity.main.MarkConfig;
import com.fighter.ace.cms.service.main.CmsLogService;
import com.fighter.ace.cms.service.main.MarkConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by hanebert on 16/6/2.
 */
@Controller
public class ConfigAct {
    private static final Logger log = LoggerFactory.getLogger(ConfigAct.class);
    private static final String TITLE_UPDATED = "修改水印设置";
    private static final long MARK_CONFIG_ID = 1L;

    @Autowired
    private MarkConfigService markConfigService;
    @Autowired
    private CmsLogService cmsLogService;

    @RequestMapping("/config/v_mark_edit.do")
    public String markEdit(HttpServletRequest request, ModelMap model) {
        MarkConfig markConfig = markConfigService.getById(MARK_CONFIG_ID);
        model.addAttribute("markConfig",markConfig);
        return "config/mark_edit";
    }

    @RequestMapping("/config/o_mark_update.do")
    public String markUpdate(MarkConfig bean,
                             HttpServletRequest request, ModelMap model) {
        markConfigService.update(bean);
        log.info("update markConfig of CmsConfig.");
        return markEdit(request, model);
    }


}
