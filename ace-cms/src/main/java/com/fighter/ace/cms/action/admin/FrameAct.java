package com.fighter.ace.cms.action.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by hanebert on 17/9/22.
 */
@Controller
public class FrameAct {

    @RequestMapping("/frame/user_main.do")
    public String userMain(ModelMap model) {
        return "frame/user_main";
    }

    @RequestMapping("/frame/user_left.do")
    public String userLeft(ModelMap model) {
        return "frame/user_left";
    }

    @RequestMapping("/frame/user_right.do")
    public String userRight(ModelMap model) {
        return "frame/user_right";
    }

}
