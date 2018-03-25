package com.fighter.ace.cms.action.admin.external;

import com.fighter.ace.cms.entity.external.Mall;
import com.fighter.ace.cms.service.external.MallService;
import com.fighter.ace.cms.service.main.CmsLogService;
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
 * Created by hanebert on 18/2/2.
 */
@Controller
public class MallAct {

    private static final Logger log = LoggerFactory.getLogger(MallAct.class);

    private static final String TITLE_SAVED = "新增商品";
    private static final String TITLE_UPDATED = "修改商品";
    private static final String TITLE_DELETED = "删除商品";
    private static final String TITLE_CHECKED = "审核商品";

    private static final int PAGESIZE = 20;


    @Autowired
    private MallService mallService;
    @Autowired
    private CmsLogService cmsLogService;

    @RequestMapping("/mall/v_list.do")
    public String getNewsList(String pageNo, String name,String type, ModelMap modelMap) {
        if (pageNo == null || "".equals(pageNo)) {
            pageNo = "1";
        }
        try {
            PageParam pageParam = new PageParam(Integer.parseInt(pageNo), PAGESIZE);
            Map<String, Object> paramMap = new HashMap<>();
            if (name != null && !"".equals(name)) {
                paramMap.put("name", "%" + name + "%");
                modelMap.addAttribute("name", name);
            }
            if (StringUtils.isNotBlank(type)){
                paramMap.put("type", Integer.valueOf(type));
                modelMap.addAttribute("type",type);
            }
            PageBean pageBean = mallService.getListPage(pageParam, paramMap);
            modelMap.addAttribute("pageBean", pageBean);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("getNewsList error", e);
        }
        return "mall/list";
    }

    @RequestMapping("/mall/v_add.do")
    public String add() {
        return "mall/add";
    }

    @RequestMapping("/mall/o_save.do")
    public String save(Mall mall, String typeImg,HttpServletRequest request){
        try {
            if (StringUtils.isNotBlank(typeImg)){
                mall.setPicUrl(typeImg);
            }
            mallService.createMall(mall);
        }catch (Exception e){
            log.error("save error",e);
        }
        return "redirect:v_list.do";
    }

    @RequestMapping("/mall/v_edit.do")
    public String edit(String id, String pageNo,ModelMap modelMap) {
        try {
            Mall mall = mallService.getById(Long.valueOf(id));
            modelMap.addAttribute("mall",mall);

            modelMap.addAttribute("id",id);
            modelMap.addAttribute("pageNo",pageNo);
        } catch (Exception e){
            log.error("edit error",e);
        }
        return "mall/edit";
    }

    @RequestMapping("/mall/o_update.do")
    public String update(String pageNo,Long id,Mall mall, String typeImg,HttpServletRequest request ){
        try {
            mall.setId(id);
            if (StringUtils.isNotBlank(typeImg)){
                mall.setPicUrl(typeImg);
            }
            mallService.updateMall(mall);
            //新增操作日志
            cmsLogService.record(request, TITLE_UPDATED, "id=" + id + ";title=" + mall.getName());
        } catch (Exception e){
            log.error("update error",e);
        }
        return "redirect:v_list.do?pageNo=" + pageNo;
    }


    @RequestMapping(value = "/mall/o_delete.do")
    public String delete(String id,HttpServletRequest request ,HttpServletResponse response){
        try {
            mallService.deleteById(Long.valueOf(id));
            //新增操作日志
            cmsLogService.record(request, TITLE_DELETED, "id=" + id);
        }catch (Exception e){
            log.error("delete error",e);
        }

        return "redirect:v_list.do";
    }


    @RequestMapping("/mall/batchDelete.do")
    public String batchDeleteArticle(Long[] ids, String pageNo, HttpServletRequest request) {
        mallService.deleteBatch(ids);
        //新增操作日志
        for (int i = 0; i < ids.length; i++) {
            cmsLogService.record(request, TITLE_DELETED, "id=" + ids[i]);
        }
        return "redirect:v_list.do?pageNo=" + pageNo;
    }

    @RequestMapping("/mall/check.do")
    public String checkArticle(Long[] ids, String pageNo, HttpServletRequest request) {
        try {
            mallService.approveBatch(ids);
            //新增操作日志
            for (int i = 0; i < ids.length; i++) {
                cmsLogService.record(request, TITLE_CHECKED, "id=" + ids[i]);
            }
        }catch (Exception e){
            log.error("checkArticle error",e);
        }
        return "redirect:v_list.do?pageNo=" + pageNo;
    }

}
