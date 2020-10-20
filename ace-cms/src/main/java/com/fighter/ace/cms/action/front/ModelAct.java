package com.fighter.ace.cms.action.front;

import com.alibaba.fastjson.JSONObject;
import com.fighter.ace.cms.Constants;
import com.fighter.ace.cms.entity.external.Category;
import com.fighter.ace.cms.entity.external.Member;
import com.fighter.ace.cms.entity.external.Model;
import com.fighter.ace.cms.manager.external.ModelManager;
import com.fighter.ace.cms.service.external.CategoryService;
import com.fighter.ace.cms.service.external.DownloadService;
import com.fighter.ace.cms.service.external.MemberService;
import com.fighter.ace.cms.service.external.ModelService;
import com.fighter.ace.cms.util.ConfigUtil;
import com.fighter.ace.cms.util.JsonUtil;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;
import com.fighter.ace.framework.web.RequestUtils;
import com.fighter.ace.framework.web.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
    @Resource
    private ConfigUtil configUtil;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private DownloadService downloadService;

    @Resource
    private ModelManager modelManager;

    @RequestMapping("/model/downCheck")
    public void downloadCheck(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap){
        //检查是否登录
        Object loginUser = request.getSession().getAttribute(Constants.MEMBER_SESSION_KEY);
        if (null == loginUser){
            ResponseUtils.renderJson(response, JsonUtil.toJson("msg", "needLogin"));
            return;
        }
        try {
            Member member = (Member) loginUser;
            //会员积分数据
            Member m = memberService.getById(member.getId());
            Float point = Float.valueOf(m.getPoint());
            Float coins = Float.valueOf(m.getCoin());
            //模型数据
            String modelId = RequestUtils.getQueryParam(request, "modelId");

            //检查是否曾经下载过
            if (downloadService.hasDownload(m.getId(),Long.valueOf(modelId))){
                ResponseUtils.renderJson(response,JsonUtil.toJson("msg", "already"));
                return;
            }
            Model modelForDown = modelManager.getById(Long.valueOf(modelId));
            //modelService.getById(Long.valueOf(modelId));
            int payType = 0;
            JSONObject data = new JSONObject();
            //检查积分
            if (payType == 0 && point.floatValue() >= modelForDown.getPoint()){
                payType = 1;
                data.put("cost",modelForDown.getPoint());
            }
            //检查金币
            if (payType == 0 && coins.floatValue() >= modelForDown.getCoin()){
                payType = 2;
                data.put("cost",modelForDown.getCoin());
            }
            data.put("payType",payType);
            data.put("msg","ok");

            ResponseUtils.renderJson(response,data.toJSONString());
        } catch (Exception e){
            ResponseUtils.renderJson(response, JsonUtil.toJson("msg", "error"));
        }
    }

    @RequestMapping("/model/download")
    public void downloadFile(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap){
        //检查是否登录
        Object loginUser = request.getSession().getAttribute(Constants.MEMBER_SESSION_KEY);
        if (null == loginUser){
            ResponseUtils.renderJson(response, JsonUtil.toJson("msg", "needLogin"));
            return;
        }
        try {
            Member member = (Member) loginUser;
            //会员积分数据
            Member m = memberService.getById(member.getId());
            Float point = Float.valueOf(m.getPoint());
            Float coins = Float.valueOf(m.getCoin());
            //模型数据
            String modelId = RequestUtils.getQueryParam(request,"modelId");


            Model modelForDown =  modelService.getById(Long.valueOf(modelId));
            int payType = 0;
            float cost = 0;
            //检查积分
            if (payType == 0 && point.floatValue() >= modelForDown.getPoint()){
                payType = 1;
                cost = modelForDown.getPoint();
            }
            //检查金币
            if (payType == 0 && coins.floatValue() >= modelForDown.getCoin()){
                payType = 2;
                cost = modelForDown.getCoin();
            }
            if (payType == 0){
                ResponseUtils.renderJson(response,JsonUtil.toJson("msg","notEnough"));
                return;
            }

            //检查是否曾经下载过
            if (!downloadService.hasDownload(m.getId(),Long.valueOf(modelId))){
                //记录消费记录
                member = memberService.addCost(m,modelForDown.getId(),payType,cost);
                request.getSession().setAttribute(Constants.MEMBER_SESSION_KEY, member);
            }

            String uuid = UUID.randomUUID().toString();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("domain","jz3d");
            jsonObject.put("memberId",member.getId());
            jsonObject.put("payType",payType);
            jsonObject.put("modelId", modelId);
            jsonObject.put("fileName", modelForDown.getModelPath());
            redisTemplate.opsForValue().set(uuid, jsonObject.toJSONString(), 30, TimeUnit.MINUTES);


            String downloadUrl = configUtil.getDownloadPrefix() + uuid;
            JSONObject data = new JSONObject();
            data.put("msg","ok");
            data.put("downloadUrl",downloadUrl);
            ResponseUtils.renderJson(response,data.toJSONString());
        } catch (Exception e){
            log.error("downloadFile error",e);
            ResponseUtils.renderJson(response, JsonUtil.toJson("msg", "error"));
        }

    }



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


    @RequestMapping("/model/{memberId}/list")
    public String findListByMemberId(@PathVariable("memberId") Long memberId,Long categoryId,String pageNo, ModelMap model){
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
            PageParam pageParam = new PageParam(getPageNo(pageNo),16);
            Map<String,Object> params = new HashMap<>();
            params.put("memberId",memberId);
            PageBean pageBean =  modelService.findListByMemberId(pageParam, params);
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
