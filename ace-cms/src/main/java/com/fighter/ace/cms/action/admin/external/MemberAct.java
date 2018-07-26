package com.fighter.ace.cms.action.admin.external;

import com.fighter.ace.cms.entity.external.Member;
import com.fighter.ace.cms.service.external.MemberService;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kehwa on 2016/5/28.
 */
@Controller
public class MemberAct {

    private static final Logger log = LoggerFactory.getLogger(MemberAct.class);
    @Autowired
    private MemberService memberService;

    public static final int PAGESIZE = 20;


    @RequestMapping(value = "/member/v_list.do")
    public String memberList(String pageNo, String username,String phone,String type,
                             HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
        if (StringUtils.isBlank(pageNo)){
            pageNo = "1";
        }
        try {
            Map<String,Object> paramMap = new HashMap<>();
            if (StringUtils.isNotBlank(username)){
                paramMap.put("username", "%" + username + "%");
                modelMap.addAttribute("username",username);
            }
            if (StringUtils.isNotBlank(phone)){
                paramMap.put("phone", "%" + phone + "%");
                modelMap.addAttribute("phone",phone);
            }
            if (StringUtils.isNotBlank(type)){
                paramMap.put("type", Integer.valueOf(type));
                modelMap.addAttribute("type",type);
            }
            PageParam pageParam = new PageParam(Integer.parseInt(pageNo), PAGESIZE);
            PageBean pageBean = memberService.getListPage(pageParam,paramMap);
            modelMap.addAttribute(pageBean);
        } catch (Exception e){
            log.error("memberList error",e);
        }
        return "member/list";
    }

    @RequestMapping(value = "/member/delete.do")
    public String memberDelete(String id,String pageNo ,HttpServletRequest request ,HttpServletResponse response){

        try {
            memberService.updateStatusById(Integer.parseInt(id));
        } catch (Exception e){
            log.error("memberDelete error",e);
        }

        return "redirect:v_list.do?pageNo ="+pageNo;
    }

    @RequestMapping(value = "/member/batchDelete.do")
    public String memberBatchDelete(int[] ids,String pageNo ,HttpServletRequest request ,HttpServletResponse response){
        try {
            memberService.updateBatchById(ids);
        } catch (Exception e){
            log.error("memberBatchDelete error",e);
        }
        return "redirect:v_list.do?pageNo ="+pageNo;
    }


    @RequestMapping(value = "/member/v_edit.do")
    public String edit(String id,String pageNo ,HttpServletRequest request ,HttpServletResponse response,ModelMap modelMap){
        try {
            Member member = memberService.getById(Long.valueOf(id));
            modelMap.addAttribute("member",member);
            modelMap.addAttribute("pageNo",pageNo);
        }catch (Exception e){
            log.error("memberToUpdate error",e);
        }
        return "member/edit";
    }

    @RequestMapping(value = "/member/o_update.do")
    public String update(String id,String pageNo ,HttpServletRequest request ,HttpServletResponse response,ModelMap modelMap){
        try {
            Member member = memberService.getById(Long.valueOf(id));
        } catch (Exception e){
            log.error("update error",e);
        }

        return "redirect:v_list.do?pageNo ="+pageNo;
    }

    @RequestMapping(value = "/member/v_check.do")
     public String vCheckMember(String id,String pageNo ,HttpServletRequest request ,HttpServletResponse response,ModelMap modelMap){
        try {
            Member member = memberService.getById(Long.valueOf(id));
            modelMap.addAttribute("member",member);
            modelMap.addAttribute("pageNo",pageNo);
        } catch (Exception e){
            log.error("vCheckMember error",e);
        }

        return "member/check";
    }

    @RequestMapping(value = "/member/o_check.do")
    public String oCheckMember(String status,String id,String pageNo ,HttpServletRequest request ,HttpServletResponse response,ModelMap modelMap){
        try {
            Member member = new Member();
            member.setId(Long.valueOf(id));
            member.setStatus(Integer.valueOf(status));
            if (status.equals("1")){
                member.setType(1);
            }
            memberService.update(member);
        }catch (Exception e){
            log.error("oCheckMember error",e);
        }
        return "redirect:v_list.do?pageNo ="+pageNo;
    }

    @RequestMapping(value = "/designer/list.do")
    public String designerList(String pageNo, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
        if (pageNo == null || "".equals(pageNo)){
            pageNo = "1";
        }
        try {
            PageParam pageParam = new PageParam(Integer.parseInt(pageNo), PAGESIZE);
            Map<String, Object> paramMap = new HashMap();
            paramMap.put("type",1);
            PageBean pageBean = memberService.getListPage(pageParam,paramMap);
            modelMap.addAttribute(pageBean);
        } catch (Exception e){
            log.error("designerList error",e);
        }
        return "designer/list";
    }

    @RequestMapping(value = "/designer/delete.do")
    public String designerDelete(String id,String pageNo ,HttpServletRequest request ,HttpServletResponse response){

        try {
            memberService.updateStatusById(Integer.parseInt(id));
        } catch (Exception e){
            log.error("designerDelete error",e);
        }
        return "redirect:v_list.do?pageNo ="+pageNo;
    }

    @RequestMapping(value = "/designer/batchDelete.do")
    public String designerBatchDelete(int[] ids,String pageNo ,HttpServletRequest request ,HttpServletResponse response){
        try {
            memberService.updateBatchById(ids);
        } catch (Exception e){
            log.error("designerBatchDelete error",e);
        }

        return "redirect:v_list.do?pageNo ="+pageNo;
    }
}
