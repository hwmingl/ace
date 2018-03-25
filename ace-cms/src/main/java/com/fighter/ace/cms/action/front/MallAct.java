package com.fighter.ace.cms.action.front;

import com.fighter.ace.cms.service.external.MallService;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hanebert on 18/1/20.
 */
@Controller
public class MallAct extends BaseAction{

    private static final Logger log = LoggerFactory.getLogger(MallAct.class);

    @Autowired
    private MallService mallService;

    @RequestMapping("/mall/index")
    public String mallIndex(String pageNo ,Integer type, ModelMap model){

        try {
            PageParam pageParam = new PageParam(getPageNo(pageNo),20);
            Map<String,Object> paramMap = new HashMap<>();
            if (null != type){
                paramMap.put("type",type);
                model.addAttribute("type", type);

                model.addAttribute("show",String.valueOf(type));
            } else {
                model.addAttribute("show","all");
            }
            paramMap.put("status", 1);

            PageBean pageBean = mallService.getListPage(pageParam,paramMap);
            model.addAttribute("pageBean", pageBean);
        } catch (Exception e){
            log.error("mallIndex error",e);
        }
        model.addAttribute("position","mall");
        return "mall/index";
    }

}
