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
public class ModelAct extends BaseAction {

    private static final Logger log = LoggerFactory.getLogger(ModelAct.class);

    public String findLatestList(Integer queryOrderBy,String pageNo, ModelMap model){

        model.addAttribute("position","model");
        return "model/list";
    }

    public String findHotList(Integer queryOrderBy,String pageNo, ModelMap model){

        model.addAttribute("position","model");
        return "model/list";
    }

    @RequestMapping("/model/list")
    public String findList(Long categoryId, Integer queryOrderBy,String pageNo, ModelMap model){
        model.addAttribute("position","model");
        return "model/list";
    }

}
