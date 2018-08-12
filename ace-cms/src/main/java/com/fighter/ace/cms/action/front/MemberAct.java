package com.fighter.ace.cms.action.front;

import com.fighter.ace.cms.entity.external.Member;
import com.fighter.ace.cms.service.external.MemberService;
import com.fighter.ace.cms.util.JsonUtil;
import com.fighter.ace.framework.web.RequestUtils;
import com.fighter.ace.framework.web.ResponseUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by hanebert on 18/7/21.
 */
@Controller
public class MemberAct extends BaseAction{

    private static final Logger log = LoggerFactory.getLogger(MemberAct.class);

    @Resource
    private MemberService memberService;

    @RequestMapping("/m/login.do")
    public void login(HttpServletRequest request , HttpServletResponse response , ModelMap modelMap){

        String account = RequestUtils.getQueryParam(request, "account");
        String password = RequestUtils.getQueryParam(request, "password");
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)){
            ResponseUtils.renderJson(response, JsonUtil.toJson("msg","invalid"));
        }

        try {
            Member member = memberService.findByPhoneAndUserName(account,password);
            if (null != member){
                ResponseUtils.renderJson(response, JsonUtil.toJson("msg","ok"));
            } else {
                ResponseUtils.renderJson(response, JsonUtil.toJson("msg","invalid"));
            }
        } catch (Exception e){
            log.error("login error",e);
        }
    }


    @RequestMapping("/m/v_index")
    public String v_index(HttpServletRequest request , HttpServletResponse response , ModelMap modelMap){

        return "/m/index";
    }

}
