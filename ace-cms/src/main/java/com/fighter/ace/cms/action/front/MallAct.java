package com.fighter.ace.cms.action.front;

import com.fighter.ace.cms.Constants;
import com.fighter.ace.cms.entity.external.Category;
import com.fighter.ace.cms.entity.external.Mall;
import com.fighter.ace.cms.entity.external.Member;
import com.fighter.ace.cms.entity.external.Model;
import com.fighter.ace.cms.service.external.AddrService;
import com.fighter.ace.cms.service.external.MallService;
import com.fighter.ace.cms.service.external.ModelService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
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
    @Resource
    private ModelService modelService;
    @Resource
    private AddrService addrService;

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


    @RequestMapping(value = "mall/detail/{id}",method = RequestMethod.GET)
    public String getDetail(@PathVariable("id") Long id,HttpServletRequest request, ModelMap modelMap){

        try{
            Mall model = mallService.getById(id);
            modelMap.addAttribute("mall", model);


            //相关素材
            PageBean pageBean =  getPageList(null, 5, "1");
            modelMap.addAttribute("rankList",pageBean.getRecordList());


            modelMap.addAttribute("position","mall");
        }catch (Exception e){
            log.error("getDetail error," + e.getMessage(),e);
        }
        return "mall/detail";
    }

    private PageBean getPageList(Long categoryId, Integer queryOrderBy,String pageNo) {
        PageParam pageParam = new PageParam(getPageNo(pageNo),10);
        return modelService.findList(pageParam,null,categoryId,null,queryOrderBy,1);
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

    @RequestMapping("/mall/delItem")
    public String o_cart_del(HttpServletRequest request,HttpServletResponse response , ModelMap modelMap){
        try {
            List<Mall> cartList = new ArrayList<>();
            Object loginObj = request.getSession().getAttribute(Constants.MEMBER_SESSION_KEY);
            if (null != loginObj){
                Object cartObj = request.getSession().getAttribute(Constants.MEMBER_CART);
                if (null != cartObj){
                    Map<Long,Mall> mallMap = (Map<Long, Mall>) cartObj;
                    Long itemId = Long.valueOf(RequestUtils.getQueryParam(request, "itemId"));
                    mallMap.remove(itemId);

                    if (mallMap.isEmpty()){
                        modelMap.addAttribute("cartListCount", 0);
                    } else {
                        cartList.addAll(mallMap.values());
                        modelMap.addAttribute("cartListCount", 1);
                        modelMap.addAttribute("cartList",cartList);
                    }
                } else {
                    modelMap.addAttribute("cartListCount", 0);
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
                } else {
                    modelMap.addAttribute("cartListCount", 0);
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


    @RequestMapping("/mall/itemsPay")
    public String v_itemsPay(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){

        try {
            List<Mall> cartList = new ArrayList<>();
            Object loginObj = request.getSession().getAttribute(Constants.MEMBER_SESSION_KEY);
            if (null != loginObj){
                Member loginUser = (Member) loginObj;
                request.setAttribute("loginUser", loginUser);

                Object cartObj = request.getSession().getAttribute(Constants.MEMBER_CART);
                if (null != cartObj){
                    Map<Long,Mall> mallMap = (Map<Long, Mall>) cartObj;
                    cartList.addAll(mallMap.values());
                    Double totalFee = 0.00;

                    for (Mall mall : cartList){
                        totalFee += Double.valueOf(mall.getPrice());
                    }




                    modelMap.addAttribute("cartListCount", 1);
                    modelMap.addAttribute("cartList",cartList);
                    modelMap.addAttribute("totalFee",totalFee);
                } else {
                    return "redirect:myCart";
                }


                PageParam pageParam = new PageParam(1, PAGESIZE);
                Map<String, Object> map = new HashMap<>();
                map.put("memberId", loginUser.getId());
                PageBean pageBean = addrService.getListPage(pageParam, map);
                modelMap.addAttribute("addrList",pageBean.getRecordList());

            } else {
                return "redirect:/cms/goLogin.do";
            }

        }catch (Exception e){
            log.error("my cart error",e);
        }
        modelMap.addAttribute("position","mall");
        return "mall/items_pay";
    }



}
