package com.fighter.ace.cms.action.front;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by hanebert on 17/9/12.
 */
@Controller
public class WelcomeAct {

    @RequestMapping("/index.do")
    public String index(HttpServletRequest request,
                        HttpServletResponse response, ModelMap model) {
        model.addAttribute("position","index");

        return "index";
    }


}
