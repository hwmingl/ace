package com.fighter.ace.cms.action.admin.external;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fighter.ace.cms.entity.external.*;
import com.fighter.ace.cms.service.external.OrdersFacadeService;
import com.fighter.ace.cms.service.main.CmsLogService;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;
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
 * 商城订单管理
 * Created by hanebert on 2019/8/11.
 */
@Controller
public class ShopAct {

    private static final Logger log = LoggerFactory.getLogger(ShopAct.class);
    public static final int PAGESIZE = 20;


    @Resource
    private CmsLogService cmsLogService;
    @Resource
    private OrdersFacadeService ordersFacadeService;


    @RequestMapping("/shop/v_list.do")
    public String getShopList(String pageNo, String nickName, ModelMap modelMap) {
        if (pageNo == null || "".equals(pageNo)) {
            pageNo = "1";
        }
        try {
            PageParam pageParam = new PageParam(Integer.parseInt(pageNo), PAGESIZE);
            Map<String, Object> map = new HashMap<>();
            if (nickName != null && !"".equals(nickName)) {
                map.put("nickName", "%" + nickName + "%");
                modelMap.addAttribute("queryNickName", nickName);
            }
            PageBean pageBean = ordersFacadeService.getListPage(pageParam, map);

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
            modelMap.addAttribute("pageBean", pageBean);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("getShopList error", e);
        }
        return "shop/list";
    }


    /**
     * 编辑
     * @param id
     * @param pageNo
     * @param modelMap
     * @return
     */
    @RequestMapping("/shop/v_edit.do")
    public String edit(String id, String pageNo,ModelMap modelMap) {
        try {
            Orders orders = ordersFacadeService.getById(Long.valueOf(id));
            modelMap.addAttribute("orders",orders);

            modelMap.addAttribute("id", id);
            modelMap.addAttribute("pageNo",pageNo);
        } catch (Exception e){
            log.error("edit error",e);
        }
        return "shop/edit";
    }

    /**
     * 更新配送数据
     * @param id
     * @param ext4
     * @param ext5
     * @param request
     * @return
     */
    @RequestMapping("/shop/update.do")
    public  String update(String id ,String ext4,String ext5 ,HttpServletRequest request){

        try {
            Orders orders = new Orders();
            orders.setId(Long.valueOf(id));
            orders.setExt4(ext4);
            orders.setExt5(ext5);
            ordersFacadeService.updateById(orders);
            cmsLogService.record(request, "更新配送数据", "id=" + id);
        } catch (Exception e){
            log.error("update error", e);
        }
        return "redirect:v_list.do";
    }

    /**
     * 删除订单
     * @param id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/shop/o_delete.do")
    public String delete(String id,HttpServletRequest request ,HttpServletResponse response){
        try {
            Orders orders = new Orders();
            orders.setId(Long.valueOf(id));
            orders.setStatus(-1);
            ordersFacadeService.updateById(orders);
            //新增操作日志
            cmsLogService.record(request, "订单删除", "id=" + id);
        }catch (Exception e){
            log.error("delete error",e);
        }
        return "redirect:v_list.do";
    }


    /**
     * 订单详情
     * @param id
     * @param pageNo
     * @param modelMap
     * @return
     */
    @RequestMapping("/shop/v_detail.do")
    public String detail(String id, String pageNo,ModelMap modelMap) {
        try {
            Orders orders = ordersFacadeService.getById(Long.valueOf(id));
            modelMap.addAttribute("orders", orders);

            List<Mall> mallList = JSON.parseArray(orders.getExt2(),Mall.class);
            modelMap.addAttribute("mallList",mallList);

            Addr addr = JSON.parseObject(orders.getExt3(),Addr.class);
            modelMap.addAttribute("addr",addr);

        } catch (Exception e){
            log.error("detail error",e);
        }
        return "shop/detail";
    }

}
