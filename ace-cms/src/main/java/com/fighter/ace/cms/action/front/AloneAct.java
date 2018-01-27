package com.fighter.ace.cms.action.front;

import com.fighter.ace.cms.entity.external.SinglePage;
import com.fighter.ace.cms.service.external.SinglePageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by hanebert on 18/1/20.
 */
@Controller
public class AloneAct  extends BaseAction{

    private static final Logger log = LoggerFactory.getLogger(AloneAct.class);

    @Autowired
    private SinglePageService singlePageService;

    @RequestMapping(value = "alone/detail", method = RequestMethod.GET)
    public String getAlone(HttpServletRequest request, ModelMap model) {
        try {
            SinglePage alone1 = singlePageService.getById(1L);
            model.addAttribute("alone1",alone1);
            SinglePage alone2 = singlePageService.getById(2L);
            model.addAttribute("alone2",alone2);
            SinglePage alone3 = singlePageService.getById(3L);
            model.addAttribute("alone3",alone3);
            SinglePage alone4 = singlePageService.getById(4L);
            model.addAttribute("alone4",alone4);
            SinglePage alone5 = singlePageService.getById(5L);
            model.addAttribute("alone5",alone5);
            SinglePage alone6 = singlePageService.getById(6L);
            model.addAttribute("alone6",alone6);
            SinglePage alone7 = singlePageService.getById(7L);
            model.addAttribute("alone7",alone7);
            SinglePage alone8 = singlePageService.getById(8L);
            model.addAttribute("alone8",alone8);
            SinglePage alone9 = singlePageService.getById(9L);
            model.addAttribute("alone9",alone9);

            model.addAttribute("position","alone");
        }catch (Exception e){
            log.error("getAlone error,",e);
        }
        return "alone/detail";
    }

}
