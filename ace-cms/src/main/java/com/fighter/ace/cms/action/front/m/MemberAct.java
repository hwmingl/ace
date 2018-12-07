package com.fighter.ace.cms.action.front.m;

import com.fighter.ace.cms.Constants;
import com.fighter.ace.cms.action.front.BaseAction;
import com.fighter.ace.cms.entity.external.Member;
import com.fighter.ace.cms.entity.external.Model;
import com.fighter.ace.cms.service.external.CategoryService;
import com.fighter.ace.cms.service.external.DownloadService;
import com.fighter.ace.cms.service.external.MemberService;
import com.fighter.ace.cms.service.external.ModelService;
import com.fighter.ace.cms.util.JsonUtil;
import com.fighter.ace.cms.vo.CategoryTreeDTO;
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
import java.util.List;
import java.util.Map;

/**
 * Created by hanebert on 18/7/21.
 */
@Controller
public class MemberAct extends BaseAction {

    private static final Logger log = LoggerFactory.getLogger(MemberAct.class);
    private static final String MODEL_PREFIX = "SC";

    @Resource
    private MemberService memberService;
    @Resource
    private DownloadService downloadService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private ModelService modelService;


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

    /**
     *
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/m/uploadModel")
    public String uploadModel(HttpServletRequest request,HttpServletResponse response , ModelMap modelMap){
        Member loginUser = (Member) request.getSession().getAttribute(Constants.MEMBER_SESSION_KEY);
        request.setAttribute("loginUser",loginUser);
        request.setAttribute("menu","upload");

        try {
            //类目Tree
            List<CategoryTreeDTO> treeDtoList = categoryService.getCategoryTree();
            modelMap.addAttribute("treeDtoList",treeDtoList);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("modelUpload error", e);
        }
        return "m/uploadModel";
    }

    @RequestMapping("/m/createUploadRecord")
    public void createUploadRecord(HttpServletRequest request,HttpServletResponse response , ModelMap modelMap){
        Member loginUser = (Member) request.getSession().getAttribute(Constants.MEMBER_SESSION_KEY);
        request.setAttribute("loginUser",loginUser);
        request.setAttribute("menu","uploadRecords");

        try {
            //var params = {"categoryId":categoryId,"name":name,"keyword":keyword,"length":length,"width":width,"height":height,"mark":mark,"modelImgs":modelImgs,"modelFile":modelFile};
            String categoryId = RequestUtils.getQueryParam(request,"categoryId");
            String name = RequestUtils.getQueryParam(request,"name");
            String keyword = RequestUtils.getQueryParam(request,"keyword");
            String length = RequestUtils.getQueryParam(request,"length");
            String width = RequestUtils.getQueryParam(request,"width");
            String height = RequestUtils.getQueryParam(request,"height");
            String mark = RequestUtils.getQueryParam(request,"mark");
            String modelImg = RequestUtils.getQueryParam(request,"modelImg");
            String modelImgs = RequestUtils.getQueryParam(request,"modelImgs");
            String modelFile = RequestUtils.getQueryParam(request,"modelFile");
            String softVersion = RequestUtils.getQueryParam(request,"softVersion");

            Model model = new Model();
            model.setMemberId(loginUser.getId());
            model.setCategoryId(Long.valueOf(categoryId));
            model.setName(name);
            model.setKeyword(keyword);
            model.setLength(length);
            model.setWidth(width);
            model.setHeight(height);

            model.setPicUrl(modelImg);
            model.setPicUrls(modelImgs);
            model.setModelPath(modelFile);
            model.setMark(mark);
            model.setSoftVersion(softVersion);

            model.setModelNo(MODEL_PREFIX + System.currentTimeMillis());
            model.setStyle(".STL");
            model.setCoin(0);
            model.setPoint(0);
            model.setStatus(0);
            model.setDownCount(0);
            model.setDownLimit(0);
            model.setRank(0);
            modelService.createModel(model);

            ResponseUtils.renderJson(response, JsonUtil.toJson("msg", "ok"));
        } catch (Exception e) {
            ResponseUtils.renderJson(response, JsonUtil.toJson("msg", "error"));
            log.error("createUploadRecord error", e);
        }
    }


    @RequestMapping("/m/uploadRecords")
    public String uploadRecords(String pageNo,HttpServletRequest request,HttpServletResponse response , ModelMap modelMap){
        Member loginUser = (Member) request.getSession().getAttribute(Constants.MEMBER_SESSION_KEY);
        request.setAttribute("loginUser",loginUser);
        request.setAttribute("menu","uploadRecords");

        try {
            PageParam pageParam = new PageParam(getPageNo(pageNo), 10);
            Map<String, Object> map = new HashMap<>();
            map.put("memberId",loginUser.getId());
            map.put("queryOrderBy", 1);

            PageBean pageBean = modelService.findListByMemberId(pageParam, map);
            modelMap.addAttribute("pageBean", pageBean);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("modelUpload error", e);
        }
        return "m/uploadRecords";
    }


    /**
     * 我要提现
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/m/v_exchangeCash")
    public String v_exchangeCash(HttpServletRequest request,HttpServletResponse response , ModelMap modelMap){
        Member loginUser = (Member) request.getSession().getAttribute(Constants.MEMBER_SESSION_KEY);
        request.setAttribute("loginUser",loginUser);
        request.setAttribute("menu","exchangeCash");

        try {
            Double coins = Integer.valueOf(loginUser.getCoin()) * 0.9 / 20;
            request.setAttribute("coins",coins);







        } catch (Exception e) {
            e.printStackTrace();
            log.error("exchangeCash error", e);
        }
        return "m/exchangeCash";
    }

    /**
     * 我要充值
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/m/v_recharge")
    public String v_recharge(HttpServletRequest request,HttpServletResponse response , ModelMap modelMap){
        Member loginUser = (Member) request.getSession().getAttribute(Constants.MEMBER_SESSION_KEY);
        request.setAttribute("loginUser",loginUser);
        request.setAttribute("menu","recharge");

        try {

        } catch (Exception e) {
            e.printStackTrace();
            log.error("recharge error", e);
        }
        return "m/recharge";
    }


}
