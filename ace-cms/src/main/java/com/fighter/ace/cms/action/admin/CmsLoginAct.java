package com.fighter.ace.cms.action.admin;

import com.fighter.ace.cms.entity.main.User;
import com.fighter.ace.framework.utils.MD5Util;
import com.fighter.ace.cms.service.UserService;
import com.fighter.ace.cms.util.CmsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanebert on 17/9/7.
 */
@Controller
public class CmsLoginAct {

    private static final Logger log = LoggerFactory.getLogger(CmsLoginAct.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login.do", method = RequestMethod.GET)
    public String input(HttpServletRequest request,
                        HttpServletResponse response, ModelMap model){
        return "login";
    }


    @RequestMapping(value = "/login.do", method = RequestMethod.POST)
    public String submit(String username, String password, String captcha, String message,
                         HttpServletRequest request, HttpServletResponse response,
                         ModelMap model) {
        try {
            User user = userService.getByUserName(username);
            if (password != null && user != null && MD5Util.md5Encode(password).equals(user.getUserPwd())){
                HttpSession session = request.getSession();
                session.setAttribute(CmsUtil.ADMIN_USER_KEY, user);
                return "index";
            }
        } catch (Exception e){
            log.error("getByUserName error",e);
        }
        log.info("user login error,by user:{},password:{}", username, password);
        List<String> errors = new ArrayList<String>();
        errors.add("账号或密码错误");
        model.addAttribute("errors", errors);
        return "login";
    }

}
