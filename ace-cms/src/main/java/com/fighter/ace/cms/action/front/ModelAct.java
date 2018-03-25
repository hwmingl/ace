package com.fighter.ace.cms.action.front;

import com.fighter.ace.cms.entity.external.Category;
import com.fighter.ace.cms.entity.external.Member;
import com.fighter.ace.cms.entity.external.Model;
import com.fighter.ace.cms.service.external.CategoryService;
import com.fighter.ace.cms.service.external.MemberService;
import com.fighter.ace.cms.service.external.ModelService;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hanebert on 18/1/20.
 */
@Controller
public class ModelAct extends BaseAction {

    private static final Logger log = LoggerFactory.getLogger(ModelAct.class);

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ModelService modelService;
    @Autowired
    private MemberService memberService;

    @RequestMapping("/model/latest")
    public String findLatest(Long categoryId,String pageNo, ModelMap model){
        try {
            List<Category> parentList = categoryService.findListByParentId(-1L);
            model.addAttribute("parentList",parentList);

            if (null == categoryId){
                categoryId = -1L;
                model.addAttribute("showParent",-1L);
            } else {
                Category category =  categoryService.getById(categoryId);
                if (category.getParentId() == -1L){
                    model.addAttribute("showParent",categoryId);
                    model.addAttribute("showSub",-1L);

                    List<Category> categoryList = categoryService.findListByParentId(categoryId);
                    model.addAttribute("categoryList",categoryList);
                } else {
                    model.addAttribute("showParent",category.getParentId());
                    model.addAttribute("showSub",categoryId);

                    List<Category> categoryList = categoryService.findListByParentId(category.getParentId());
                    model.addAttribute("categoryList",categoryList);
                }

                model.addAttribute("tipName",category.getName());
            }
            //
            PageBean pageBean = getPageList(categoryId,1,pageNo);
            model.addAttribute("pageBean",pageBean);
        } catch (Exception e){
            log.error("findList error",e);
        }
        model.addAttribute("position","model");
        return "model/latest";
    }

    @RequestMapping("/model/hotList")
    public String findHotList(Long categoryId,String pageNo, ModelMap model){
        try {
            List<Category> parentList = categoryService.findListByParentId(-1L);
            model.addAttribute("parentList",parentList);

            if (null == categoryId){
                categoryId = -1L;
                model.addAttribute("showParent",-1L);
            } else {
                Category category =  categoryService.getById(categoryId);
                if (category.getParentId() == -1L){
                    model.addAttribute("showParent",categoryId);
                    model.addAttribute("showSub",-1L);

                    List<Category> categoryList = categoryService.findListByParentId(categoryId);
                    model.addAttribute("categoryList",categoryList);
                } else {
                    model.addAttribute("showParent",category.getParentId());
                    model.addAttribute("showSub",categoryId);

                    List<Category> categoryList = categoryService.findListByParentId(category.getParentId());
                    model.addAttribute("categoryList",categoryList);
                }

                model.addAttribute("tipName",category.getName());
            }
            //
            PageBean pageBean = getPageList(categoryId,3,pageNo);
            model.addAttribute("pageBean",pageBean);
        } catch (Exception e){
            log.error("findList error",e);
        }
        model.addAttribute("position","model");
        return "model/hotList";
    }
    @RequestMapping("/model/list")
    public String findList(Long categoryId,String pageNo, ModelMap model){
        try {
            List<Category> parentList = categoryService.findListByParentId(-1L);
            model.addAttribute("parentList",parentList);

            if (null == categoryId){
                categoryId = -1L;
                model.addAttribute("showParent",-1L);
            } else {
                Category category =  categoryService.getById(categoryId);
                if (category.getParentId() == -1L){
                    model.addAttribute("showParent",categoryId);
                    model.addAttribute("showSub",-1L);

                    List<Category> categoryList = categoryService.findListByParentId(categoryId);
                    model.addAttribute("categoryList",categoryList);
                } else {
                    model.addAttribute("showParent",category.getParentId());
                    model.addAttribute("showSub",categoryId);

                    List<Category> categoryList = categoryService.findListByParentId(category.getParentId());
                    model.addAttribute("categoryList",categoryList);
                }

                model.addAttribute("tipName",category.getName());
            }
            //
            PageBean pageBean = getPageList(categoryId,5,pageNo);
            model.addAttribute("pageBean",pageBean);
        } catch (Exception e){
            log.error("findList error",e);
        }
        model.addAttribute("position","model");
        return "model/list";
    }


    @RequestMapping(value = "model/detail/{id}",method = RequestMethod.GET)
    public String getDetail(@PathVariable("id") Long id,HttpServletRequest request, ModelMap modelMap){

        try{
            Model model = modelService.getById(id);
            modelMap.addAttribute("model", model);

            Member member = memberService.getById(model.getMemberId());
            modelMap.addAttribute("member",member);

            Category category =  categoryService.getById(model.getCategoryId());
            modelMap.addAttribute("category",category);

            //相关素材
            PageBean pageBean =  getPageList(model.getCategoryId(),5,"1");
            modelMap.addAttribute("rankList",pageBean.getRecordList());


            modelMap.addAttribute("position","model");
        }catch (Exception e){
            log.error("getDetail error," + e.getMessage(),e);
        }
        return "model/detail";
    }


    private PageBean getPageList(Long categoryId, Integer queryOrderBy,String pageNo) {
        PageParam pageParam = new PageParam(getPageNo(pageNo),16);
        return modelService.findList(pageParam,null,categoryId,null,queryOrderBy,1);
    }

}
