package com.fighter.ace.framework.web.interceptor;

import com.fighter.ace.cms.entity.main.CmsUser;
import com.fighter.ace.cms.security.CmsThreadVariable;
import com.fighter.ace.cms.service.CmsUserService;
import com.fighter.ace.cms.util.CmsUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

/**
 * CMS上下文信息拦截器
 *
 * 包括登录信息、权限信息、站点信息
 * Created by hanebert on 17/9/23.
 */
public class AdminContextInterceptor extends HandlerInterceptorAdapter {

    public static final String PERMISSION_MODEL = "_permission_key";

    //可以进行编码、安全控制等处理
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 获得用户
        CmsUser user = null;
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            String username =  (String) subject.getPrincipal();
            user = cmsUserService.getByUserName(username);
        }
        // User加入线程变量
        CmsThreadVariable.setUser(user);
        // URI
        String uri = getURI(request);
        if (exclude(uri)) {
            return true;
        }
        //System.out.println(getURI(request));
        return super.preHandle(request, response, handler);
    }

    //有机会修改ModelAndView
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mav) throws Exception {
        CmsUser user = CmsUtil.getAdminUser(request);
        // 不控制权限时perm为null，PermistionDirective标签将以此作为依据不处理权限问题。
        if (auth && user != null && !user.isSuper() && mav != null
                && mav.getModelMap() != null && mav.getViewName() != null
                && !mav.getViewName().startsWith("redirect:")) {
            mav.getModelMap().addAttribute(PERMISSION_MODEL, getUserPermission(user));
        }
    }

    //可以根据ex是否为null判断是否发生了异常，进行日志记录
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // Sevlet容器有可能使用线程池，所以必须手动清空线程变量。
        CmsThreadVariable.removeUser();
    }


    private boolean exclude(String uri) {
        if (excludeUrls != null) {
            for (String exc : excludeUrls) {
                if (exc.equals(uri)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获得第三个路径分隔符的位置
     *
     * @param request
     * @throws IllegalStateException
     *             访问路径错误，没有三(四)个'/'
     */
    private static String getURI(HttpServletRequest request)
            throws IllegalStateException {
        UrlPathHelper helper = new UrlPathHelper();
        String uri = helper.getOriginatingRequestUri(request);
        String ctxPath = helper.getOriginatingContextPath(request);
        int start = 0, i = 0, count = 2;
        if (!StringUtils.isBlank(ctxPath)) {
            count++;
        }
        while (i < count && start != -1) {
            start = uri.indexOf('/', start + 1);
            i++;
        }

        if (start <= 0) {
            throw new IllegalStateException(
                    "admin access path not like '/jeeadmin/jeecms/...' pattern: "
                            + uri);
        }
        return uri.substring(start);
    }


    private Set<String> getUserPermission(CmsUser user){
        Set<String>viewPermissionSet = new HashSet<String>();
        Set<String> perms = new HashSet<>();
        perms.add("member:v_list.do");

        Set<String> userPermission = new HashSet<String>();
        if(perms!=null){
            for(String perm : perms){
                perm = "/" + perm;
                if(perm.contains(":")){
                    perm=perm.replace(":", "/").replace("*", "");
                }
                userPermission.add(perm);
            }
        }
        return userPermission;
    }


    private CmsUserService cmsUserService;
    private boolean auth = true;
    private String[] excludeUrls;

    public void setCmsUserService(CmsUserService cmsUserService) {
        this.cmsUserService = cmsUserService;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    public void setExcludeUrls(String[] excludeUrls) {
        this.excludeUrls = excludeUrls;
    }
}
