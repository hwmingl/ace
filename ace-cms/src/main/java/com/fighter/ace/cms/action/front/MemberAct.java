package com.fighter.ace.cms.action.front;

import com.fighter.ace.cms.entity.external.Member;
import com.fighter.ace.cms.service.external.MemberService;
import com.fighter.ace.cms.util.JsonUtil;
import com.fighter.ace.framework.web.RequestUtils;
import com.fighter.ace.framework.web.ResponseUtils;
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

        try {
            String phone = RequestUtils.getQueryParam(request, "phone");
            Member member = memberService.getByPhone(phone);
            if (null == member){
                ResponseUtils.renderJson(response, JsonUtil.toJson("code", "notExist"));
            } else {
                ResponseUtils.renderJson(response, JsonUtil.toJson("code", "ok"));
            }

        } catch (Exception e){

        }




    }


}
