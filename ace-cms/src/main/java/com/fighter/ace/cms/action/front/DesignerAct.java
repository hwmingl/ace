package com.fighter.ace.cms.action.front;

import com.fighter.ace.cms.service.external.MemberService;
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
public class DesignerAct extends BaseAction {

    private static final Logger log = LoggerFactory.getLogger(DesignerAct.class);

    @Autowired
    private MemberService memberService;

    @RequestMapping("/designer/index")
    public String designerIndex(String pageNo,ModelMap model){
        try {
            PageParam pageParam = new PageParam(getPageNo(pageNo),9);
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("type", 1);
            paramMap.put("status", 1);
            PageBean pageBean = memberService.getListPage(pageParam,paramMap);
            model.addAttribute("pageBean", pageBean);
        } catch (Exception e){
            log.error("designerIndex error",e);
        }
        model.addAttribute("position","designer");
        return "designer/index";
    }

}
