package com.fighter.ace.cms.action.admin.main;

import com.fighter.ace.cms.entity.main.CmsRole;
import com.fighter.ace.cms.entity.main.CmsUser;
import com.fighter.ace.cms.service.main.CmsRoleService;
import com.fighter.ace.cms.service.main.CmsUserService;
import com.fighter.ace.framework.web.RequestUtils;
import com.fighter.ace.framework.web.ResponseUtils;
import com.fighter.ace.framework.web.WebErrors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by hanebert on 17/9/24.
 */
@Controller
public class CmsRoleAct {

    private static final Logger log = LoggerFactory.getLogger(CmsRoleAct.class);
    private static final int PAGESIZE = 20;
    @Autowired
    private CmsRoleService cmsRoleService;
    @Autowired
    private CmsUserService cmsUserService;

    @RequestMapping("/role/v_list.do")
    public String list(HttpServletRequest request, ModelMap model) {
        List<CmsRole> cmsRoleList = cmsRoleService.findAll();
        model.addAttribute("recordList", cmsRoleList);
        return "role/list";
    }


    //@RequiresPermissions("role:v_add")
    @RequestMapping("/role/v_add.do")
    public String add(ModelMap model) {

        return "role/add";
    }


    @RequestMapping("/role/o_save.do")
    public String save(CmsRole bean, String[] perms,
                       HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateSave(bean, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }

        if (RequestUtils.getQueryParam(request,"all").equals("true")){
            bean.setSuper(true);
        }


        Long id = cmsRoleService.save(bean);

        /*bean = manager.save(bean, splitPerms(perms));
        log.info("save CmsRole id={}", bean.getId());
        cmsLogMng.operating(request, "cmsRole.log.save", "id=" + bean.getId()
                + ";name=" + bean.getName());*/
        return "redirect:v_list.do";
    }


    @RequestMapping("/role/check_name.do")
    public void checkRoleNameExist(HttpServletRequest request,HttpServletResponse response){
        String name = RequestUtils.getQueryParam(request, "name");
        CmsRole cmsRole = cmsRoleService.getByName(name);
        if(cmsRole == null){
            ResponseUtils.renderJson(response, "true");
        }else{
            ResponseUtils.renderJson(response, "false");
        }
    }

    //@RequiresPermissions("role:v_edit")
    @RequestMapping("/role/v_edit.do")
    public String edit(Integer id, HttpServletRequest request, ModelMap model) {
        /*WebErrors errors = validateEdit(id, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        model.addAttribute("cmsRole", manager.findById(id));*/
        return "role/edit";
    }


    @RequestMapping("/role/o_update.do")
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

    @RequestMapping("/role/o_delete.do")
    public String delete(Long id, HttpServletRequest request,
                         ModelMap model) {
        WebErrors errors = validateDelete(id,request);
        if (errors.hasErrors()){
            return errors.showErrorPage(model);
        }
        cmsRoleService.delete(id);
        return list(request, model);
    }


    private WebErrors validateSave(CmsRole bean, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        return errors;
    }

    private WebErrors validateDelete(Long id, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        List<CmsUser> cmsUserList = cmsUserService.findListByRoleId(id);
        if (null != cmsUserList && !cmsUserList.isEmpty()){
            errors.addError("该角色使用中,不可被删除");
        }
        return errors;
    }


}
