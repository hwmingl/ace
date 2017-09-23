package com.fighter.ace.cms.action.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by hanebert on 17/9/12.
 */
@Controller
public class WelcomeAct {

    @RequestMapping("/index.do")
    public String index() {
        return "index";
    }


}
