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
public class NewsAct extends BaseAction{

    private static final Logger log = LoggerFactory.getLogger(NewsAct.class);

    @RequestMapping("/news/index")
    public String listNews(String pageNo,ModelMap model){
        model.addAttribute("position","news");
        return "news/index";
    }

}
