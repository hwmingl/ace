package com.fighter.ace.cms.action.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by hanebert on 17/9/22.
 */
@Controller
public class FrameAct {

    @RequestMapping("/frame/content_main.do")
    public String contentMain(ModelMap model) {
        return "frame/content_main";
    }

    @RequestMapping("/frame/content_left.do")
    public String contentLeft(ModelMap model) {
        return "frame/content_left";
    }

    @RequestMapping("/frame/content_right.do")
    public String contentRight(ModelMap model) {
        return "frame/content_right";
    }

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
