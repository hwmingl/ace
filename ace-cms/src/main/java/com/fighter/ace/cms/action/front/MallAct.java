package com.fighter.ace.cms.action.front;

import com.fighter.ace.cms.Constants;
import com.fighter.ace.cms.entity.external.Mall;
import com.fighter.ace.cms.entity.external.Member;
import com.fighter.ace.cms.service.external.MallService;
import com.fighter.ace.cms.util.JsonUtil;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;
import com.fighter.ace.framework.web.RequestUtils;
import com.fighter.ace.framework.web.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hanebert on 18/1/20.
 */
@Controller
public class MallAct extends BaseAction{

    private static final Logger log = LoggerFactory.getLogger(MallAct.class);

    @Autowired
    private MallService mallService;

    @RequestMapping("/mall/index")
    public String mallIndex(String pageNo ,Integer type, ModelMap model){

        try {
            PageParam pageParam = new PageParam(getPageNo(pageNo),20);
            Map<String,Object> paramMap = new HashMap<>();
            if (null != type){
                paramMap.put("type",type);
                model.addAttribute("type", type);
                model.addAttribute("show",String.valueOf(type));
            } else {
                model.addAttribute("show","all");
            }
            paramMap.put("status", 1);

            PageBean pageBean = mallService.getListPage(pageParam,paramMap);
            model.addAttribute("pageBean", pageBean);
        } catch (Exception e){
            log.error("mallIndex error",e);
        }
        model.addAttribute("position","mall");
        return "mall/index";
    }

    @RequestMapping("/mall/add")
    public void o_cart_add(HttpServletRequest request,HttpServletResponse response , ModelMap modelMap){

        Object loginObj = request.getSession().getAttribute(Constants.MEMBER_SESSION_KEY);
        try {
            if (null == loginObj){
                ResponseUtils.renderJson(response, JsonUtil.toJson("msg", "needLogin"));
                return;
            }
            Map<Long,Mall> mallMap = null;
            Member loginUser = (Member)loginObj;
            Object cartObj = request.getSession().getAttribute(Constants.MEMBER_CART);
            if (null == cartObj){
                mallMap = new HashMap<>();
            } else {
                mallMap = (Map<Long, Mall>) cartObj;
            }

            Long itemId = Long.valueOf(RequestUtils.getQueryParam(request, "itemId"));

            //添加商品至map
            Mall item = mallService.getById(itemId);
            if (null != item){
                mallMap.put(itemId, item);
            }
            request.getSession().setAttribute(Constants.MEMBER_CART, mallMap);
            ResponseUtils.renderJson(response, JsonUtil.toJson("msg", "ok"));

        } catch (Exception e) {
            ResponseUtils.renderJson(response, JsonUtil.toJson("msg", "invalid"));
            log.error("recharge error", e);
        }
    }



    /**
     * 我的购物车
     * @param modelMap
     * @return
     */
    @RequestMapping("/mall/myCart")
    public String v_myCart(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){

        try {
            List<Mall> cartList = new ArrayList<>();
            Object loginObj = request.getSession().getAttribute(Constants.MEMBER_SESSION_KEY);
            if (null != loginObj){
                Object cartObj = request.getSession().getAttribute(Constants.MEMBER_CART);
                if (null != cartObj){
                    Map<Long,Mall> mallMap = (Map<Long, Mall>) cartObj;
                    cartList.addAll(mallMap.values());
                    modelMap.addAttribute("cartListCount", 1);
                    modelMap.addAttribute("cartList",cartList);
                }

            } else {
                modelMap.addAttribute("cartListCount", 0);
            }
            Member loginUser = (Member) loginObj;
            request.setAttribute("loginUser", loginUser);
        }catch (Exception e){
            log.error("my cart error",e);
        }
        modelMap.addAttribute("position","mall");
        return "mall/cart";
    }






}
