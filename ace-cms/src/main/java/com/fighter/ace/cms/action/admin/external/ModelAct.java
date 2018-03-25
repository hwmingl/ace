package com.fighter.ace.cms.action.admin.external;

import com.fighter.ace.cms.Constants;
import com.fighter.ace.cms.entity.external.Model;
import com.fighter.ace.cms.service.external.CategoryService;
import com.fighter.ace.cms.service.external.ModelService;
import com.fighter.ace.cms.service.main.CmsLogService;
import com.fighter.ace.cms.vo.CategoryTreeDTO;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;
import com.fighter.ace.framework.web.ResponseUtils;
import com.fighter.ace.framework.web.WebErrors;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

/**
 * Created by hanebert on 16/6/4.
 */
@Controller
public class ModelAct {

    private static final Logger log = LoggerFactory.getLogger(ModelAct.class);
    private static final String MODEL_PREFIX = "SC";
    private static final int PAGESIZE = 20;

    private static final String TITLE_DELETED = "删除模型";
    private static final String TITLE_NOT_APPROVED = "模型审核打回";
    private static final String TITLE_APPROVED = "模型审核通过";

    @Autowired
    private ModelService modelService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CmsLogService cmsLogService;

    @RequestMapping(value = "/model/v_add.do")
    public String add(HttpServletRequest request, ModelMap model) {
        return "model/add";
    }

    @RequestMapping(value = "/model/o_save.do")
    public String save(HttpServletRequest request, ModelMap model,Model modelDto,
                       String mediaPath,String typeImg) {
        WebErrors errors = WebErrors.create(request);
        try{
            modelDto.setModelPath(mediaPath);
            modelDto.setPicUrl(typeImg);
            modelDto.setKeyword(modelDto.getName());
            String filePath = request.getSession().getServletContext().getRealPath("/")+mediaPath;
            File file = new File(filePath);
            modelDto.setSize(String.valueOf(file.length()));
            modelDto.setModelNo(MODEL_PREFIX + System.currentTimeMillis());
            modelDto.setSoftVersion("3DS MAX 2009");

            System.out.print(file.length());
            System.out.println(mediaPath);
            System.out.println(typeImg);
            modelService.createModel(modelDto);
        }catch (Exception e){
            e.printStackTrace();
            log.error("Model save error," + e.getMessage(), e);
            errors.addErrorString("Model save error,"+e.getMessage());
        }
        if (errors.hasErrors()) {
            model.addAttribute("errors", errors.getErrors());
            return "model/add";
        }
        return "redirect:v_list.do";
    }

    @RequestMapping(value = "/model/v_list.do")
    public String list(String pageNo,String name,String memberName,Long categoryId,String queryOrderBy,HttpServletRequest request,
                       ModelMap model) {
        if (StringUtils.isBlank(pageNo)){
            pageNo = "1";
        }
        if (StringUtils.isBlank(name)){
            name = null;
        }
        if (StringUtils.isBlank(memberName)){
            memberName = null;
        }
        if (StringUtils.isBlank(queryOrderBy)){
            queryOrderBy = "1";
        }
        PageParam pageParam = new PageParam(Integer.parseInt(pageNo), PAGESIZE);
        PageBean pageBean = modelService.findList(pageParam,name,categoryId,memberName,Integer.valueOf(queryOrderBy),null);
        model.addAttribute("pageBean",pageBean);
        //类目Tree
        List<CategoryTreeDTO> treeDtoList = categoryService.getCategoryTree();
        model.addAttribute("treeDtoList",treeDtoList);
        model.addAttribute("pageNo",pageNo);
        model.addAttribute("name",name);
        model.addAttribute("memberName",memberName);
        model.addAttribute("queryOrderBy",queryOrderBy);
        model.addAttribute("categoryId",categoryId);
        return "model/list";
    }

    @RequestMapping(value = "/model/v_rank.do")
    public String updateRankView(String pageNo,Long id,HttpServletRequest request,
                                ModelMap model){
        WebErrors errors = WebErrors.create(request);
        try{
            Model modelDto = modelService.getById(id);
            model.put("model",modelDto);
            model.put("pageNo",pageNo);
        }catch (Exception e){
            e.printStackTrace();
            log.error("updateRankView error," + e.getMessage(), e);
            errors.addErrorString("updateRankView error,"+e.getMessage());
        }
        return "model/rank";
    }

    @RequestMapping(value = "/model/o_rank.do")
    public String updateRank(String pageNo,Integer rank,Long id,HttpServletRequest request,
                            ModelMap model){
        WebErrors errors = WebErrors.create(request);
        try{
            Model modelDto = new Model();
            modelDto.setId(id);
            modelDto.setRank(rank);
            modelService.update(modelDto);
        }catch (Exception e){
            e.printStackTrace();
            log.error("updateRank error," + e.getMessage(), e);
            errors.addErrorString("updateRank error,"+e.getMessage());
        }
        return "redirect:v_list.do?pageNo="+pageNo;
    }

    @RequestMapping(value = "/model/o_ok.do")
    public void approveOk(Long id,HttpServletRequest request,HttpServletResponse response,
                             ModelMap model){
        try{
            Model modelDto = new Model();
            modelDto.setId(id);
            modelDto.setStatus(1);
            modelService.update(modelDto);
            //get model from db
            //modelDto = modelService.getById(id);
            //同步缩略图和模型文件至素材库
            /*List<FileBean> fileBeans = new ArrayList<>();
            String realPath = request.getRealPath("/");
            if (StringUtils.isNotBlank(modelDto.getPicUrl())){
                File pic = new File(realPath + modelDto.getPicUrl());
                if (pic.exists()){
                    FileBean picFile = new FileBean();
                    picFile.setFile(pic);
                    picFile.setDestDir(StringUtil.getDestDir(modelDto.getPicUrl()));
                    fileBeans.add(picFile);
                }
            }
            if (StringUtils.isNotBlank(modelDto.getModelPath())){
                File pic = new File(realPath + modelDto.getModelPath());
                if (pic.exists()){
                    FileBean modelFile = new FileBean();
                    modelFile.setFile(pic);
                    modelFile.setDestDir(StringUtil.getDestDir(modelDto.getModelPath()));
                    fileBeans.add(modelFile);
                }
            }
            if (!fileBeans.isEmpty()){
                String uploadResult =  RemoteUpload.upload(Constants.LIB_UPLOAD_URL, fileBeans);
                log.info("upload to media lib result:"+uploadResult);
            }*/

            ResponseUtils.renderJson(response, "{'result':'success'}");
            //记录操作日志
            cmsLogService.record(request, TITLE_APPROVED, "id=" + id);
        }catch (Exception e){
            e.printStackTrace();
            log.error("updateRank error," + e.getMessage(), e);
            ResponseUtils.renderJson(response, "{'result':'error'}");
        }
    }

    @RequestMapping(value = "/model/o_back.do")
    public void approveBack(Long id,HttpServletRequest request,HttpServletResponse response,
                          ModelMap model){
        try{
            Model modelDto = new Model();
            modelDto.setId(id);
            modelDto.setStatus(Constants.STATUS_BACK);
            modelService.update(modelDto);
            ResponseUtils.renderJson(response, "{'result':'success'}");
            //记录操作日志
            cmsLogService.record(request, TITLE_NOT_APPROVED, "id=" + id);
        }catch (Exception e){
            e.printStackTrace();
            log.error("updateRank error," + e.getMessage(), e);
            ResponseUtils.renderJson(response, "{'result':'error'}");
        }
    }

    @RequestMapping("/model/o_deleteById.do")
    public void deleteById(Long id,HttpServletRequest request, HttpServletResponse response,ModelMap model) {
        try{
            modelService.deleteById(id);
            //记录操作日志
            cmsLogService.record(request, TITLE_DELETED, "id=" + id);
            ResponseUtils.renderJson(response, "{'result':'success'}");
        }catch (Exception e){
            e.printStackTrace();
            ResponseUtils.renderJson(response, "{'result':'error'}");
        }

    }

    @RequestMapping("/model/o_delete.do")
    public String deleteBatch(Long[] ids,HttpServletRequest request, ModelMap model) {
        modelService.deleteByIds(ids);
        log.info("delete models {}", ids);
        //记录操作日志
        for (int i = 0; i < ids.length; i++) {
            cmsLogService.record(request, TITLE_DELETED, "id=" + ids[i]);
        }
        return "redirect:v_list.do";
    }

    @RequestMapping("/model/o_check.do")
    public String approveByBatch(String pageNo,Long[] ids,HttpServletRequest request, ModelMap model) {
        try{
            modelService.approveByIds(ids);
            log.info("approve models {}", ids);
            //记录操作日志
            for (int i = 0; i < ids.length; i++) {
                cmsLogService.record(request, TITLE_APPROVED, "id=" + ids[i]);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("approve by ids error",e);
        }

        return "redirect:v_list.do?pageNo="+pageNo;
    }



    @RequestMapping(value = "/model/v_edit.do")
    public String v_edit(String pageNo,String id,HttpServletRequest request,
                       ModelMap modelMap) {
        if (StringUtils.isBlank(pageNo)){
            pageNo = "1";
        }
        Model modelDto = modelService.getById(Long.valueOf(id));
        modelMap.addAttribute("model",modelDto);
        modelMap.addAttribute("pageNo",pageNo);
        return "model/edit";
    }

    @RequestMapping(value = "/model/o_edit.do")
    public String o_edit(String pageNo,Model modDto, HttpServletRequest request, ModelMap modelMap) {
        if (StringUtils.isBlank(pageNo)){
            pageNo = "1";
        }
        modelService.update(modDto);
        return "redirect:v_list.do?pageNo="+pageNo;
    }
}
