package com.fighter.ace.cms.action.front;

import com.fighter.ace.cms.entity.external.Member;
import com.fighter.ace.cms.entity.external.News;
import com.fighter.ace.cms.service.external.MemberService;
import com.fighter.ace.cms.service.external.ModelService;
import com.fighter.ace.cms.service.external.NewsService;
import com.fighter.ace.cms.util.CmsUtil;
import com.fighter.ace.cms.util.JsonUtil;
import com.fighter.ace.cms.util.MsgUtil;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;
import com.fighter.ace.framework.web.RequestUtils;
import com.fighter.ace.framework.web.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by hanebert on 17/9/12.
 */
@Controller
public class WelcomeAct extends BaseAction{

    private static final Logger log = LoggerFactory.getLogger(WelcomeAct.class);

    @Autowired
    private ModelService modelService;

    @Autowired
    private NewsService newsService;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private MemberService memberService;

    @RequestMapping("/index.do")
    public String index(HttpServletRequest request , HttpServletResponse response , ModelMap modelMap) {
        try {
            //推荐模型
            PageBean pageBean = getPageList(-1L , 5 , "1");
            modelMap.addAttribute("rankList",pageBean.getRecordList());


            //今日看点
            List<News> dailyList = getNewsByStyle(1, 2);
            modelMap.addAttribute("dailyList",dailyList);
            //设计前沿
            List<News> designList = getNewsByStyle(2,6);
            modelMap.addAttribute("designList",designList);

        } catch (Exception e){
            log.error("index error",e);
        }
        modelMap.addAttribute("position","index");
        return "index";
    }

    /**
     * 会员注册
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/goRegister.do")
    public String goRegister(HttpServletRequest request , HttpServletResponse response , ModelMap modelMap){

        return "register";
    }

    /**
     * 会员登录
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/goLogin.do")
    public String login(HttpServletRequest request , HttpServletResponse response , ModelMap modelMap){

        return "login";
    }

    /**
     * 发送注册验证码
     * @param request
     * @param response
     * @param modelMap
     */
    @RequestMapping("/sendRegCode.do")
    public void sendRegCode(HttpServletRequest request , HttpServletResponse response , ModelMap modelMap){
        String phoneNo = RequestUtils.getQueryParam(request, "phone");
        try {
            String code = CmsUtil.getCode();
            MsgUtil.sendMessage(phoneNo,"您的注册验证码:" + code + ", 有效期300秒.");
            redisTemplate.opsForValue().set(phoneNo,code,5L, TimeUnit.MINUTES);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ResponseUtils.renderJson(response, JsonUtil.toJson("msg","ok"));
    }

    /**
     * 会员注册
     * @param request
     * @param response
     * @param modelMap
     */
    @RequestMapping("/register.do")
    public void register(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){

        try {
            String phone = RequestUtils.getQueryParam(request, "phone");
            Member m = memberService.getByPhone(phone);
            if (null != m){
                ResponseUtils.renderJson(response,JsonUtil.toJson("code","exist"));
                return;
            }

            String code = RequestUtils.getQueryParam(request, "code");
            String password = RequestUtils.getQueryParam(request, "password");

            Object data = redisTemplate.opsForValue().get(phone);
            if (null == data){
                ResponseUtils.renderJson(response,JsonUtil.toJson("code","expired"));
                return;
            } else if (!code.equals(data.toString())){
                ResponseUtils.renderJson(response,JsonUtil.toJson("code","notEqual"));
                return;
            }


            Member member = new Member();
            member.setPhone(phone);
            member.setUserName(phone);
            member.setUserPwd(password);

            memberService.addMember(member);
            ResponseUtils.renderJson(response, JsonUtil.toJson("code", "ok"));
        } catch (Exception e){
            log.error("register error",e);
        }

    }


    private PageBean getPageList(Long categoryId, Integer queryOrderBy,String pageNo) {
        PageParam pageParam = new PageParam(getPageNo(pageNo),16);
        return modelService.findList(pageParam,null,categoryId,null,queryOrderBy,1);
    }


    private List<News> getNewsByStyle(Integer style,Integer count) {
            return newsService.findListByStyle(style,count);
    }


}
