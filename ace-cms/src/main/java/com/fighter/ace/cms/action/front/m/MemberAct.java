package com.fighter.ace.cms.action.front.m;

import com.fighter.ace.cms.Constants;
import com.fighter.ace.cms.action.front.BaseAction;
import com.fighter.ace.cms.entity.external.Member;
import com.fighter.ace.cms.service.external.DownloadService;
import com.fighter.ace.cms.service.external.MemberService;
import com.fighter.ace.cms.util.JsonUtil;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;
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
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hanebert on 18/7/21.
 */
@Controller
public class MemberAct extends BaseAction {

    private static final Logger log = LoggerFactory.getLogger(MemberAct.class);

    @Resource
    private MemberService memberService;
    @Resource
    private DownloadService downloadService;

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
                request.getSession().setAttribute(Constants.MEMBER_SESSION_KEY,member);
                request.getSession().removeAttribute(Constants.MEMBER_SESSION_KEY_NULL);
                ResponseUtils.renderJson(response, JsonUtil.toJson("msg","ok"));
            } else {
                ResponseUtils.renderJson(response, JsonUtil.toJson("msg","invalid"));
            }
        } catch (Exception e){
            log.error("login error",e);
        }
    }


    @RequestMapping("/m/index")
    public String v_index(HttpServletRequest request , HttpServletResponse response , ModelMap modelMap){
        request.setAttribute("loginUser",request.getSession().getAttribute(Constants.MEMBER_SESSION_KEY));
        request.setAttribute("menu","index");
        return "m/index";
    }

    @RequestMapping("/m/downList")
    public String downList(String pageNo,HttpServletRequest request,HttpServletResponse response , ModelMap modelMap){
        Member loginUser = (Member) request.getSession().getAttribute(Constants.MEMBER_SESSION_KEY);
        request.setAttribute("loginUser",loginUser);
        request.setAttribute("menu","downList");

        try {
            PageParam pageParam = new PageParam(getPageNo(pageNo), PAGESIZE);
            Map<String, Object> map = new HashMap<>();
            map.put("memberId",loginUser.getId());
            PageBean pageBean = downloadService.getListPage(pageParam, map);
            modelMap.addAttribute("pageBean", pageBean);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("downList error", e);
        }
        return "m/downList";
    }


}
