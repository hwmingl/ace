package com.fighter.ace.cms.action.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.apache.shiro.web.filter.authc.FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME;

/**
 * Created by hanebert on 17/9/7.
 */
@Controller
public class CmsLoginAct {

    private static final Logger log = LoggerFactory.getLogger(CmsLoginAct.class);

    @RequestMapping(value = "/login.do", method = RequestMethod.GET)
    public String input(HttpServletRequest request,
                        HttpServletResponse response, ModelMap model){
        return "login";
    }


    @RequestMapping(value = "/login.do", method = RequestMethod.POST)
    public String submit(String username, String password, String captcha, String message,
                         HttpServletRequest request, HttpServletResponse response,
                         ModelMap model) {

        Object error = request.getAttribute(DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
        if (null != error) {
            model.addAttribute("error", error);
        }
        return "login";
    }

}
