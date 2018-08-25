package com.fighter.ace.cms.interceptor;

import com.fighter.ace.cms.Constants;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by hanebert on 18/8/25.
 */
public class MemberLoginInterceptor implements HandlerInterceptor {

    private String backUrl;
    private Set<String> whiteUrls = new HashSet<>();

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        String requestUri = httpServletRequest.getRequestURI();
        if (whiteUrls.contains(requestUri)){
            return true;
        }

        Object loginUser =  httpServletRequest.getSession().getAttribute(Constants.MEMBER_SESSION_KEY);
        if (null == loginUser){
            httpServletRequest.getSession().setAttribute(Constants.MEMBER_SESSION_KEY_NULL,"memberSessionIsNull");
            httpServletResponse.sendRedirect(backUrl);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    public void setBackUrl(String backUrl) {
        this.backUrl = backUrl;
    }

    public void setWhiteUrls(Set<String> whiteUrls) {
        this.whiteUrls = whiteUrls;
    }
}
