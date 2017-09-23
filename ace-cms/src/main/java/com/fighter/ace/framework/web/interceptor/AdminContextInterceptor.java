package com.fighter.ace.framework.web.interceptor;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * CMS上下文信息拦截器
 *
 * 包括登录信息、权限信息、站点信息
 * Created by hanebert on 17/9/23.
 */
public class AdminContextInterceptor extends HandlerInterceptorAdapter {


    //可以进行编码、安全控制等处理
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String uri = getURI(request);
        if (exclude(uri)) {
            return true;
        }

        //System.out.println(getURI(request));
        return super.preHandle(request, response, handler);
    }

    //有机会修改ModelAndView
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    //可以根据ex是否为null判断是否发生了异常，进行日志记录
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
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


    private boolean auth = true;
    private String[] excludeUrls;

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    public void setExcludeUrls(String[] excludeUrls) {
        this.excludeUrls = excludeUrls;
    }
}
