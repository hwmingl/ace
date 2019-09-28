package com.fighter.ace.cms.action.front.m;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fighter.ace.cms.Constants;
import com.fighter.ace.cms.action.front.BaseAction;
import com.fighter.ace.cms.entity.external.*;
import com.fighter.ace.cms.service.external.*;
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
import java.util.ArrayList;
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
    @Resource
    private NoticeService noticeService;
    @Resource
    private AddrService addrService;

    @Resource
    private OrdersFacadeService ordersFacadeService;


    @RequestMapping("/m/login.do")
    public void login(HttpServletRequest request , HttpServletResponse response , ModelMap modelMap){

        String account = RequestUtils.getQueryParam(request, "account");
        String password = RequestUtils.getQueryParam(request, "password");
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)){
            ResponseUtils.renderJson(response, JsonUtil.toJson("msg", "invalid"));
            return;
        }

        try {
            Member member = memberService.findByPhoneAndUserName(account,password);
            if (null != member){
                request.getSession().setAttribute(Constants.MEMBER_SESSION_KEY,member);
                request.getSession().removeAttribute(Constants.MEMBER_SESSION_KEY_NULL);
                ResponseUtils.renderJson(response, JsonUtil.toJson("msg","ok"));
                return;
            } else {
                ResponseUtils.renderJson(response, JsonUtil.toJson("msg","invalid"));
                return;
            }
        } catch (Exception e){
            log.error("login error",e);
        }
    }


    /**
     * 会员中心首页
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/m/index")
    public String v_index(HttpServletRequest request , HttpServletResponse response , ModelMap modelMap){
        request.setAttribute("loginUser",request.getSession().getAttribute(Constants.MEMBER_SESSION_KEY));
        request.setAttribute("menu","index");

        //系统公告
        PageParam pageParam = new PageParam(1, 4);
        PageBean pageBean = noticeService.getListPage(pageParam, new HashMap<String, Object>());
        if (pageBean.getTotalCount() > 0){
            modelMap.addAttribute("noticeList", pageBean.getRecordList());
        }

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

            //JSONArray picJson = new JSONArray();
            //String[] pics = modelImgs.split(",");
            //picJson.add(pics);

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
            request.setAttribute("coins", coins);







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



    /**
     * 修改密码
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/m/v_pwd")
    public String v_pwd(HttpServletRequest request,HttpServletResponse response , ModelMap modelMap){
        Member loginUser = (Member) request.getSession().getAttribute(Constants.MEMBER_SESSION_KEY);
        request.setAttribute("loginUser",loginUser);
        request.setAttribute("menu","resetPwd");

        try {

        } catch (Exception e) {
            e.printStackTrace();
            log.error("recharge error", e);
        }
        return "m/m_pwd";
    }

    /**
     * 修改密码
     * @param request
     * @param response
     * @param modelMap
     */
    @RequestMapping("/m/o_pwd")
    public void o_pwd(HttpServletRequest request,HttpServletResponse response , ModelMap modelMap){
        Member loginUser = (Member) request.getSession().getAttribute(Constants.MEMBER_SESSION_KEY);
        request.setAttribute("loginUser",loginUser);
        request.setAttribute("menu","resetPwd");


        String oldpwd = RequestUtils.getQueryParam(request, "oldpwd");
        String newPwd = RequestUtils.getQueryParam(request, "newPwd");

        try {
            if (!oldpwd.equals(loginUser.getUserPwd())){
                ResponseUtils.renderJson(response, JsonUtil.toJson("msg","invalid"));
                return;
            }

            Member member = new Member();
            member.setId(loginUser.getId());
            member.setUserPwd(newPwd);
            int n = memberService.update(member);
            if (n > 0){
                loginUser.setUserPwd(newPwd);
                request.getSession().setAttribute(Constants.MEMBER_SESSION_KEY, loginUser);
                ResponseUtils.renderJson(response, JsonUtil.toJson("msg","ok"));
            }
        } catch (Exception e){
            log.error("update password error",e);
        }

    }

    /**
     * 地址管理
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/m/v_addr")
    public String v_addr(String pageNo,HttpServletRequest request,HttpServletResponse response , ModelMap modelMap){
        Member loginUser = (Member) request.getSession().getAttribute(Constants.MEMBER_SESSION_KEY);
        request.setAttribute("loginUser",loginUser);
        request.setAttribute("menu","address");

        try {
            PageParam pageParam = new PageParam(getPageNo(pageNo), PAGESIZE);
            Map<String, Object> map = new HashMap<>();
            map.put("memberId", loginUser.getId());
            PageBean pageBean = addrService.getListPage(pageParam, map);
            modelMap.addAttribute("pageBean", pageBean);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("recharge error", e);
        }
        return "m/m_addr";
    }

    /**
     * 添加地址页面
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/m/v_addr_add")
    public String v_addr_add(HttpServletRequest request,HttpServletResponse response , ModelMap modelMap){
        Member loginUser = (Member) request.getSession().getAttribute(Constants.MEMBER_SESSION_KEY);
        request.setAttribute("loginUser",loginUser);
        request.setAttribute("menu","address");

        try {

        } catch (Exception e) {
            e.printStackTrace();
            log.error("recharge error", e);
        }
        return "m/m_addr_add";
    }

    /**
     * 添加地址操作
     * @param request
     * @param response
     * @param modelMap
     */
    @RequestMapping("/m/o_addr_add")
    public void o_addr_add(HttpServletRequest request,HttpServletResponse response , ModelMap modelMap){
        Member loginUser = (Member) request.getSession().getAttribute(Constants.MEMBER_SESSION_KEY);
        request.setAttribute("loginUser",loginUser);
        request.setAttribute("menu","address");

        try {
            String phone = RequestUtils.getQueryParam(request, "phone");
            String name = RequestUtils.getQueryParam(request, "name");
            String addr = RequestUtils.getQueryParam(request, "addr");

            Addr addr1 = new Addr();
            addr1.setMemberId(loginUser.getId());
            addr1.setName(name);
            addr1.setPhone(phone);
            addr1.setAddr(addr);
            Long n = addrService.addAddr(addr1);
            if (n > 0){
                ResponseUtils.renderJson(response, JsonUtil.toJson("msg","ok"));
            } else {
                ResponseUtils.renderJson(response, JsonUtil.toJson("msg","invalid"));
            }

        } catch (Exception e) {
            ResponseUtils.renderJson(response, JsonUtil.toJson("msg", "invalid"));
            log.error("recharge error", e);
        }
    }

    /**
     * 删除地址
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/m/o_addr_del")
    public String o_addr_del(HttpServletRequest request,HttpServletResponse response , ModelMap modelMap){
        Member loginUser = (Member) request.getSession().getAttribute(Constants.MEMBER_SESSION_KEY);
        request.setAttribute("loginUser",loginUser);
        request.setAttribute("menu","address");

        try {
            String addrId = RequestUtils.getQueryParam(request, "id");
            addrService.deleteAddr(Long.valueOf(addrId));
        } catch (Exception e) {
            log.error("o_addr_del error", e);
        }
        return "redirect:v_addr";
    }

    /**
     * 设置默认地址操作
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/m/o_addr_deft")
    public String o_addr_deft(HttpServletRequest request,HttpServletResponse response , ModelMap modelMap){
        Member loginUser = (Member) request.getSession().getAttribute(Constants.MEMBER_SESSION_KEY);
        request.setAttribute("loginUser",loginUser);
        request.setAttribute("menu","address");

        try {
            //取消原默认地址
            Addr deftAddr = addrService.getByDeft();
            if (null != deftAddr){
                Addr oldAddr = new Addr();
                oldAddr.setId(deftAddr.getId());
                oldAddr.setStatus(1);
                addrService.updateAddr(oldAddr);
            }

            //设置为默认地址
            String addrId = RequestUtils.getQueryParam(request, "id");
            Addr addr = new Addr();
            addr.setId(Long.valueOf(addrId));
            addr.setStatus(2);
            addrService.updateAddr(addr);

        } catch (Exception e) {
            log.error("o_addr_del error", e);
        }
        return "redirect:v_addr";
    }





    /**
     * 系统公告
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/m/v_notice")
    public String v_notice(String pageNo,HttpServletRequest request,HttpServletResponse response , ModelMap modelMap){
        Member loginUser = (Member) request.getSession().getAttribute(Constants.MEMBER_SESSION_KEY);
        request.setAttribute("loginUser",loginUser);
        request.setAttribute("menu","notice");

        try {
            PageParam pageParam = new PageParam(getPageNo(pageNo), PAGESIZE);
            Map<String, Object> map = new HashMap<>();
            PageBean pageBean = noticeService.getListPage(pageParam, map);
            modelMap.addAttribute("pageBean", pageBean);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("v_notice error", e);
        }
        return "m/m_notice";
    }


    /**
     * 开通设计师
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/m/v_designer")
    public String v_designer(HttpServletRequest request,HttpServletResponse response , ModelMap modelMap){
        Member loginUser = (Member) request.getSession().getAttribute(Constants.MEMBER_SESSION_KEY);
        request.setAttribute("loginUser",loginUser);
        request.setAttribute("menu","designer");

        try {

        } catch (Exception e) {
            e.printStackTrace();
            log.error("recharge error", e);
        }
        return "m/m_designer";
    }


    /**
     * 修改资料
     * @param request
     * @param response
     * @param modelMap
     */
    @RequestMapping("/m/o_info")
    public void o_info(HttpServletRequest request,HttpServletResponse response , ModelMap modelMap){
        Member loginUser = (Member) request.getSession().getAttribute(Constants.MEMBER_SESSION_KEY);
        request.setAttribute("loginUser",loginUser);
        request.setAttribute("menu","designer");

        try {

            String type = RequestUtils.getQueryParam(request, "type");
            String phone = RequestUtils.getQueryParam(request, "phone");
            String cardNo = RequestUtils.getQueryParam(request, "cardNo");
            String nickName = RequestUtils.getQueryParam(request, "nickName");
            Member member = new Member();
            member.setId(loginUser.getId());
            member.setPhone(phone);
            member.setCardNo(cardNo);
            member.setType(Integer.valueOf(type));

            int n = memberService.update(member);
            if (n > 0){
                loginUser.setPhone(phone);
                loginUser.setCardNo(cardNo);
                loginUser.setType(Integer.valueOf(type));
                loginUser.setNickName(nickName);
                request.getSession().setAttribute(Constants.MEMBER_SESSION_KEY, loginUser);
                ResponseUtils.renderJson(response, JsonUtil.toJson("msg","ok"));
            } else {
                ResponseUtils.renderJson(response, JsonUtil.toJson("msg","invalid"));
            }

        } catch (Exception e) {
            ResponseUtils.renderJson(response, JsonUtil.toJson("msg", "invalid"));
            log.error("recharge error", e);
        }
    }

    /**
     * 更换头像页面
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/m/v_avatar")
    public String v_avatar(HttpServletRequest request,HttpServletResponse response , ModelMap modelMap){
        Member loginUser = (Member) request.getSession().getAttribute(Constants.MEMBER_SESSION_KEY);
        request.setAttribute("loginUser",loginUser);
        request.setAttribute("menu","designer");

        try {

        } catch (Exception e) {
            e.printStackTrace();
            log.error("recharge error", e);
        }
        return "m/m_avatar";
    }


    @RequestMapping("/m/o_avatar")
    public void o_avatar(HttpServletRequest request,HttpServletResponse response , ModelMap modelMap){
        Member loginUser = (Member) request.getSession().getAttribute(Constants.MEMBER_SESSION_KEY);
        request.setAttribute("loginUser",loginUser);
        request.setAttribute("menu","designer");

        try {

            String picUrl = RequestUtils.getQueryParam(request, "picUrl");

            Member member = new Member();
            member.setId(loginUser.getId());
            member.setPicUrl(picUrl);


            int n = memberService.update(member);
            if (n > 0){
                loginUser.setPicUrl(picUrl);
                request.getSession().setAttribute(Constants.MEMBER_SESSION_KEY, loginUser);
                ResponseUtils.renderJson(response, JsonUtil.toJson("msg","ok"));
            } else {
                ResponseUtils.renderJson(response, JsonUtil.toJson("msg","invalid"));
            }

        } catch (Exception e) {
            ResponseUtils.renderJson(response, JsonUtil.toJson("msg", "invalid"));
            log.error("recharge error", e);
        }
    }


    /**
     * 我的订单
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/m/v_order")
    public String v_order(String pageNo,HttpServletRequest request,HttpServletResponse response , ModelMap modelMap){
        Member loginUser = (Member) request.getSession().getAttribute(Constants.MEMBER_SESSION_KEY);
        request.setAttribute("loginUser",loginUser);
        request.setAttribute("menu","order");

        try {
            PageParam pageParam = new PageParam(getPageNo(pageNo), PAGESIZE);
            Map<String, Object> map = new HashMap<>();
            map.put("memberId", loginUser.getId());
            PageBean pageBean = ordersFacadeService.getListPage(pageParam,map);

            List<OrderMember> dataList = new ArrayList<>();
            List<OrderMember> itemList = pageBean.getRecordList();
            for (OrderMember orders : itemList){
                if (StringUtils.isNotBlank(orders.getExt3())){
                    JSONObject ext4Json = JSONObject.parseObject(orders.getExt3());
                    orders.setExt2(ext4Json.getString("phone"));
                    orders.setExt3(ext4Json.getString("name"));
                }
                dataList.add(orders);
            }
            pageBean.setRecordList(dataList);

            modelMap.addAttribute("pageBean",pageBean);
        } catch (Exception e) {
            log.error("v_order error", e);
        }
        return "m/m_order";
    }


    @RequestMapping("/m/v_detail")
    public String detail(String id,HttpServletRequest request,ModelMap modelMap) {
        Member loginUser = (Member) request.getSession().getAttribute(Constants.MEMBER_SESSION_KEY);
        request.setAttribute("loginUser",loginUser);
        request.setAttribute("menu","order");

        try {
            Orders orders = ordersFacadeService.getById(Long.valueOf(id));
            modelMap.addAttribute("orders", orders);

            List<Mall> mallList = JSON.parseArray(orders.getExt2(), Mall.class);
            modelMap.addAttribute("mallList", mallList);

            Addr addr = JSON.parseObject(orders.getExt3(),Addr.class);
            modelMap.addAttribute("addr",addr);

        } catch (Exception e){
            log.error("edit error",e);
        }
        return "m/m_order_detail";
    }



}
