package com.fighter.ace.cms.action.front.m;

import com.fighter.ace.cms.Constants;
import com.fighter.ace.cms.action.front.BaseAction;
import com.fighter.ace.cms.entity.external.Member;
import com.fighter.ace.cms.service.external.BillService;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;
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
 * Created by hanebert on 18/8/27.
 */
@Controller
public class BillAction extends BaseAction{

    private static final Logger log = LoggerFactory.getLogger(BillAction.class);
    @Resource
    private BillService billService;

    @RequestMapping("/m/bills")
    public String v_index(String pageNo,HttpServletRequest request,HttpServletResponse response , ModelMap modelMap){
        Object object = request.getSession().getAttribute(Constants.MEMBER_SESSION_KEY);
        request.setAttribute("loginUser",object);
        request.setAttribute("menu","bills");

        try {
            Member loginUser = (Member) object;
            PageParam pageParam = new PageParam(getPageNo(pageNo), PAGESIZE);
            Map<String, Object> map = new HashMap<>();
            map.put("memberId",loginUser.getId());
            PageBean pageBean = billService.getListPage(pageParam, map);
            modelMap.addAttribute("pageBean", pageBean);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("v_index error", e);
        }
        return "m/bills";
    }

}
