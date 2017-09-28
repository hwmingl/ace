package com.fighter.ace.cms.action.admin.main;

import com.fighter.ace.cms.entity.main.CmsRole;
import com.fighter.ace.cms.entity.main.CmsUser;
import com.fighter.ace.cms.service.CmsRoleService;
import com.fighter.ace.cms.service.CmsUserService;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;
import com.fighter.ace.framework.web.ResponseUtils;
import com.fighter.ace.framework.web.WebErrors;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by hanebert on 17/9/25.
 */
@Controller
public class CmsAdminAct {


    private static final Logger log = LoggerFactory.getLogger(CmsRoleAct.class);
    private static final int PAGESIZE = 20;

    @Autowired
    private CmsUserService cmsUserService;
    @Autowired
    private CmsRoleService cmsRoleService;

    @RequestMapping("/admin/v_list.do")
    public String list(HttpServletRequest request, ModelMap model) {
        try {
            PageBean pageBean = cmsUserService.getListPage(new PageParam(1,PAGESIZE),null);
            model.addAttribute("pageBean",pageBean);
        }catch (Exception e){
            log.error("admin list error ",e);
        }
        return "admin/list";
    }

    //@RequiresPermissions("admin:v_add")
    @RequestMapping("/admin/v_add.do")
    public String add(ModelMap model) {
        PageBean pageBean = cmsRoleService.getListPage(new PageParam(1,PAGESIZE), null);
        model.addAttribute("roleList",pageBean.getRecordList());
        return "admin/add";
    }


    @RequestMapping("/admin/o_save.do")
    public String save(CmsUser bean, String[] perms,
                       HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateSave(bean, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }

        Long id = cmsUserService.save(bean);

        /*bean = manager.save(bean, splitPerms(perms));
        log.info("save CmsRole id={}", bean.getId());
        cmsLogMng.operating(request, "cmsRole.log.save", "id=" + bean.getId()
                + ";name=" + bean.getName());*/
        return "redirect:v_list.do";
    }

    @RequestMapping("/admin/check_username.do")
    public void checkUserNameExist(String userName,HttpServletRequest request,HttpServletResponse response){
        CmsUser cmsUser = cmsUserService.getByUserName(userName);
        if(cmsUser == null){
            ResponseUtils.renderJson(response, "true");
        }else{
            ResponseUtils.renderJson(response, "false");
        }
    }

    //@RequiresPermissions("role:v_edit")
    @RequestMapping("/admin/v_edit.do")
    public String edit(Integer id, HttpServletRequest request, ModelMap model) {
        /*WebErrors errors = validateEdit(id, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        model.addAttribute("cmsRole", manager.findById(id));*/
        return "admin/edit";
    }


    @RequestMapping("/admin/o_update.do")
    public String update(CmsRole bean, String[] perms,boolean all,
                         HttpServletRequest request,HttpServletResponse response,ModelMap model) {
        /*WebErrors errors = validateUpdate(bean.getId(), request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        bean = manager.update(bean, splitPerms(perms));
        //权限更改 清空用户权限缓存
        if(hasChangePermission(all, perms, bean)){
            Set<CmsUser> admins=bean.getUsers();
            for(CmsUser admin:admins){
                authorizingRealm.removeUserAuthorizationInfoCache(admin.getUsername());
            }
        }
        log.info("update CmsRole id={}.", bean.getId());
        cmsLogMng.operating(request, "cmsRole.log.update", "id=" + bean.getId()
                + ";name=" + bean.getName());*/
        return list(request, model);
    }


    private WebErrors validateSave(CmsUser bean, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        return errors;
    }

}
