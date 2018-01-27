package com.fighter.ace.cms.action.admin.main;

import com.fighter.ace.cms.service.main.CmsLogService;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hanebert on 17/9/25.
 */
@Controller
public class CmsLogAct {

    private static final Logger log = LoggerFactory.getLogger(CmsLogAct.class);
    public static final int PAGESIZE = 20;

    @Autowired
    private CmsLogService cmsLogService;

    @RequestMapping("/log/v_list.do")
    public String list(String userName,String pageNo,ModelMap modelMap) {
        try{
            Map<String, Object> map = new HashMap<>();
            if (pageNo == null || "".equals(pageNo)){
                pageNo = "1";
            }
            if(StringUtils.isNotBlank(userName)){
                map.put("userName", "%" + userName + "%");
            }
            modelMap.addAttribute("userName", userName);
            PageParam pageParam = new PageParam(Integer.parseInt(pageNo), PAGESIZE);
            PageBean pageBean = cmsLogService.getListPage(pageParam,map);
            modelMap.addAttribute("pageBean", pageBean);
        }catch (Exception e){
            e.printStackTrace();
            log.error("listLog error",e);
        }
        return "log/list";
    }

}
