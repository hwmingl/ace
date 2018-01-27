package com.fighter.ace.cms.action.admin.external;


import com.fighter.ace.cms.entity.external.SinglePage;
import com.fighter.ace.cms.service.external.SinglePageService;
import com.fighter.ace.cms.service.main.CmsLogService;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;
import com.fighter.ace.framework.web.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hanebert on 16/5/30.
 */
@Controller
public class AloneAct {

    private static final Logger log = LoggerFactory.getLogger(AloneAct.class);

    private static final String TITLE_SAVED = "新增单页";
    private static final String TITLE_UPDATED = "修改单页";

    private static final int PAGESIZE = 20;
    @Autowired
    private SinglePageService singlePageService;
    @Autowired
    private CmsLogService cmsLogService;

    @RequestMapping(value = "/alone/v_list.do")
    public String list(HttpServletRequest request, ModelMap model,String pageNo) {
        if (pageNo == null || "".equals(pageNo)) {
            pageNo = "1";
        }
        String title = RequestUtils.getQueryParam(request, "title");
        PageParam pageParam = new PageParam(Integer.valueOf(pageNo),PAGESIZE);
        Map<String, Object> map = new HashMap<>();
        if(title != null && !title.equals("")){
            map.put("title", "%"+title+"%");

        }
        PageBean pageBean = null;
        try {
            pageBean = singlePageService.getListPage(pageParam,map);
            model.addAttribute("title",title);
            model.addAttribute("pageBean", pageBean);
        }catch(Exception e){
            e.printStackTrace();
            log.error("singlePageAct getlist error", e);
        }

        return "alone/list";
    }

    @RequestMapping(value = "/alone/v_add.do")
    public String add(ModelMap model) {
        return "singlepage/add";
    }

    @RequestMapping(value = "/alone/v_edit.do")
    public String edit(Long id, String pageNo,HttpServletRequest request, ModelMap model) {
        model.addAttribute("bean",singlePageService.getById(id));
        model.addAttribute("pageNo",pageNo);
        return "alone/edit";
    }


    @RequestMapping("/alone/o_save.do")
    public String save(String title,String content, HttpServletRequest request, ModelMap model) {
        SinglePage singlePagedDto = new SinglePage();
        singlePagedDto.setTitle(title);
        singlePagedDto.setContent(content);
        try {
            long id =  singlePageService.createSinglePage(singlePagedDto);
            //记录操作日志
            cmsLogService.record(request, TITLE_SAVED, "id=" + id + ";title=" + title);
        }catch (Exception e){
            e.printStackTrace();
            log.error("insert singlePage error",e);
        }
        return "redirect:v_list.do";
    }

    @RequestMapping("/alone/o_edit.do")
    public String update(Long id, String pageNo,String title,String content, HttpServletRequest request, ModelMap model) {

        try {
            SinglePage singlePageDto = new SinglePage();
            singlePageDto.setId(id);
            singlePageDto.setTitle(title);
            singlePageDto.setContent(content);
            singlePageService.updateSinglePage(singlePageDto);
            //记录操作日志
            cmsLogService.record(request,TITLE_SAVED,"id="+id+";title="+title);
        } catch (Exception e){
            log.error("update error",e);
        }
        return "redirect:v_list.do?pageNo"+pageNo;
    }

}
