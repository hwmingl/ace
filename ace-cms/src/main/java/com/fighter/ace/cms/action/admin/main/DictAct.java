package com.fighter.ace.cms.action.admin.main;


import com.fighter.ace.cms.service.main.DictService;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by hanebert on 16/7/24.
 */
@Controller
public class DictAct {

    private static final Logger log = LoggerFactory.getLogger(DictAct.class);
    public static final int PAGESIZE = 20;
    @Autowired
    private DictService dictService;

    @RequestMapping("/dict/v_list.do")
    public String dictList(String pageNo, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
        if (pageNo == null || "".equals(pageNo)){
            pageNo = "1";
        }
        try{
            //分页查询
            PageParam pageParam = new PageParam(Integer.parseInt(pageNo), PAGESIZE);
            PageBean pageBean = dictService.getListPage(pageParam,null);
            modelMap.addAttribute("pageBean", pageBean);
        }catch (Exception e){
            e.printStackTrace();
            log.error("dictList error,"+e.getMessage(),e);
        }
        return "dict/list";
    }


}
