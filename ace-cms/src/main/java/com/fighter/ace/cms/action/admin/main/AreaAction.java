package com.fighter.ace.cms.action.admin.main;

import com.fighter.ace.cms.entity.main.Area;
import com.fighter.ace.cms.service.main.AreaService;
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
import java.util.List;
import java.util.Map;

/**
 * Created by kehwa on 2016/8/6.
 */
@Controller
public class AreaAction {
    private static final Logger log = LoggerFactory.getLogger(AreaAction.class);
    private static final String Area_SAVED = "更新地名";
    @Autowired
    private AreaService areaService;
    @Autowired
    private CmsLogService cmsLogService;

    @RequestMapping(value = "/area/v_list.do")
      public String listArea(HttpServletRequest request,String pageNo,ModelMap modelMap,String reid){

        if (pageNo == null || "".equals(pageNo)){
            pageNo = "1";
        }

        if(reid ==null || "0".equals(reid)){
            reid ="0" ;
        }

        try{
            //分页查询
            PageParam pageParam = new PageParam(Integer.parseInt(pageNo), 20);
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("reid",reid);
            PageBean pageBean = areaService.getListPage(pageParam,paramMap);
            modelMap.addAttribute("pageBean", pageBean);
            if (!reid.equals("0")){
                modelMap.addAttribute("fArea", areaService.getById(Long.valueOf(reid)).getName());
            }
        }catch (Exception e){
            log.error("dictList error,"+e.getMessage(),e);
        }

        return "area/list";
    }

    @RequestMapping(value = "/area/v_update.do")
    public String vUpdateArea(HttpServletRequest request,ModelMap modelMap,String id){
        modelMap.addAttribute("area",areaService.getById(Long.valueOf(id)));
        return "area/edit";
    }

    @RequestMapping(value = "/area/o_update.do")
    public String oUpdateArea(HttpServletRequest request,ModelMap modelMap,Area area,String preId){
        areaService.updateArea(area);
        cmsLogService.record(request, Area_SAVED, "id=" + area.getId());
        return listArea(request,null,modelMap,preId);
    }
}
