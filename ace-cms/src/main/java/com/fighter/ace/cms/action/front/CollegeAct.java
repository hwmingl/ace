package com.fighter.ace.cms.action.front;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by hanebert on 18/1/20.
 */
@Controller
public class CollegeAct extends BaseAction {

    private static final Logger log = LoggerFactory.getLogger(CollegeAct.class);

    @RequestMapping("/college/index")
    public String listDesigner(String pageNo,ModelMap model){
        model.addAttribute("position","college");
        return "college/index";
    }

}
