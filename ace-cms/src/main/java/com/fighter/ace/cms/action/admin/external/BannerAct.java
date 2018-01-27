package com.fighter.ace.cms.action.admin.external;

import com.fighter.ace.cms.entity.external.Banner;
import com.fighter.ace.cms.service.external.BannerService;
import com.fighter.ace.cms.service.main.CmsLogService;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * Created by hanebert on 16/8/6.
 */
@Controller
public class BannerAct {

    private static final Logger log = LoggerFactory.getLogger(BannerAct.class);
    private static final String TITLE_UPDATED = "修改Banner";
    public static final int PAGESIZE = 20;

    @Autowired
    private BannerService bannerService;
    @Autowired
    private CmsLogService cmsLogService;

    @RequestMapping("/banner/v_list.do")
    public String listBanner(String pageNo,ModelMap modelMap){
        if (pageNo == null || "".equals(pageNo)){
            pageNo = "1";
        }
        try{
            PageParam pageParam = new PageParam(Integer.parseInt(pageNo), PAGESIZE);
            PageBean pageBean = bannerService.ListPage(pageParam,new HashMap<String, Object>());
            modelMap.addAttribute("pageBean", pageBean);
        }catch (Exception e){
            e.printStackTrace();
            log.error("listBanner error",e);
        }
        return "banner/list";
    }

    @RequestMapping("/banner/v_edit.do")
    public String edit(Long id,ModelMap modelMap){
        try{
            Banner banner = bannerService.getById(id);
            modelMap.addAttribute("banner", banner);
        }catch (Exception e){
            e.printStackTrace();
            log.error("edit error", e);
        }
        return "banner/edit";
    }

    @RequestMapping("/banner/o_save.do")
    public String save(Long id,String link,String typeImg,HttpServletRequest request,ModelMap modelMap){
        try{
            Banner banner = new Banner();
            banner.setId(id);
            banner.setLink(link);
            banner.setPicUrl(typeImg);
            bannerService.update(banner);
            //记录操作日志
            cmsLogService.record(request, TITLE_UPDATED, "id=" + banner.getId());
        }catch (Exception e){
            e.printStackTrace();
            log.error("save error", e);
        }
        return "redirect:v_list.do";
    }

}
