package com.fighter.ace.cms.action.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Properties;

/**
 * Created by hanebert on 16/5/28.
 */
@Controller
public class WelcomeAct {

    @RequestMapping("/index.do")
    public String index() {
        return "index";
    }


    @RequestMapping("/top.do")
    public String top(HttpServletRequest request, ModelMap model) {
        //model.addAttribute("user", user);
        return "top";
    }

    @RequestMapping("/main.do")
    public String main() {
        return "main";
    }

    @RequestMapping("/left.do")
    public String left() {
        return "left";
    }

    @RequestMapping("/right.do")
    public String right(HttpServletRequest request, ModelMap model) {
        Properties props = System.getProperties();
        Runtime runtime = Runtime.getRuntime();
        long freeMemoery = runtime.freeMemory();
        long totalMemory = runtime.totalMemory();
        long usedMemory = totalMemory - freeMemoery;
        long maxMemory = runtime.maxMemory();
        long useableMemory = maxMemory - totalMemory + freeMemoery;
        model.addAttribute("props", props);
        model.addAttribute("freeMemoery", freeMemoery);
        model.addAttribute("totalMemory", totalMemory);
        model.addAttribute("usedMemory", usedMemory);
        model.addAttribute("maxMemory", maxMemory);
        model.addAttribute("useableMemory", useableMemory);
        //model.addAttribute("version", version);
        //model.addAttribute("user", user);
        //model.addAttribute("flowMap", cmsStatisticSvc.getWelcomeSiteFlowData(site.getId()));
        return "right";
    }
}
