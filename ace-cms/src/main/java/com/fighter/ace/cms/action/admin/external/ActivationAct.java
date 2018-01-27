package com.fighter.ace.cms.action.admin.external;


import com.fighter.ace.cms.service.external.ActivationService;
import com.fighter.ace.cms.service.main.CmsLogService;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;
import com.fighter.ace.framework.web.RequestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hanebert on 16/6/28.
 */
@Controller
public class ActivationAct {

    private static final Logger log = LoggerFactory.getLogger(ActivationAct.class);
    public static final int PAGESIZE = 20;

    private static final String TITLE_SAVED = "新增激活码";
    private static final String TITLE_UPDATED = "修改激活码";
    private static final String TITLE_DELETED = "删除激活码";

    @Autowired
    private ActivationService activationService;
    @Autowired
    private CmsLogService cmsLogService;

    @RequestMapping(value = "/activation/v_list.do")
    public String list(String pageNo,String code,HttpServletRequest request,
                       ModelMap model) {
        if (StringUtils.isBlank(pageNo)){
            pageNo = "1";
        }
        PageParam pageParam = new PageParam(Integer.parseInt(pageNo), PAGESIZE);
        PageBean pageBean = activationService.findList(pageParam, code);
        model.addAttribute("pageBean",pageBean);
        model.addAttribute("pageNo",pageNo);
        model.addAttribute("code",code);
        return "activation/list";
    }

    @RequestMapping(value = "/activation/v_add.do")
    public String add(HttpServletRequest request, ModelMap model) {
        return "activation/add";
    }

    @RequestMapping(value = "/activation/o_save.do")
    public String save(HttpServletRequest request, ModelMap model,String expiredTime,Integer num,Integer point) {

        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(expiredTime);
            activationService.save(date,num,point);
        }catch (Exception e){
            e.printStackTrace();
            log.error("activation save error," + e.getMessage(), e);
        }
        return "redirect:v_list.do";
    }


    @RequestMapping("/activation/delete_single.do")
    public String deleteSingle(HttpServletRequest request, ModelMap model) {
        String id = RequestUtils.getQueryParam(request, "id");
        activationService.deleteById(Long.valueOf(id));
        log.info("delete activation {}", id);
        //记录操作日志
        cmsLogService.record(request, TITLE_DELETED, "id=" + id);
        return "redirect:list.do";
    }

    @RequestMapping("/activation/batchDelete.do")
    public String deleteBatch(long[] ids,HttpServletRequest request, ModelMap model) {
        activationService.deleteByIds(ids);
        log.info("delete activation {}", ids);
        //记录操作日志
        for (int i = 0; i < ids.length; i++) {
            cmsLogService.record(request, TITLE_DELETED, "id=" + ids[i]);
        }
        return "redirect:list.do";
    }



}
