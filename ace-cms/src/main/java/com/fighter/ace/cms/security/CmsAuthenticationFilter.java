package com.fighter.ace.cms.security;

import com.fighter.ace.cms.entity.main.CmsRole;
import com.fighter.ace.cms.entity.main.CmsUser;
import com.fighter.ace.cms.service.CmsRoleService;
import com.fighter.ace.cms.service.CmsUserService;
import com.fighter.ace.cms.util.CmsUtil;
import com.fighter.ace.framework.utils.MD5Util;
import com.fighter.ace.framework.web.RequestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * CmsAuthenticationFilter自定义登录认证filter
 */
public class CmsAuthenticationFilter extends FormAuthenticationFilter {
	
	private Logger logger = LoggerFactory.getLogger(CmsAuthenticationFilter.class);
	
	public static final String COOKIE_ERROR_REMAINING = "_error_remaining";
	/**
	 * 验证码名称
	 */
	public static final String CAPTCHA_PARAM = "captcha";
	/**
	 * 返回URL
	 */
	public static final String RETURN_URL = "returnUrl";
	/**
	 * 登录错误地址
	 */
	public static final String FAILURE_URL = "failureUrl";

	protected boolean executeLogin(ServletRequest request,ServletResponse response) throws Exception {
		AuthenticationToken token = createToken(request, response);
		if (token == null) {
			String msg = "create AuthenticationToken error";
			throw new IllegalStateException(msg);
		}
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String username = (String) token.getPrincipal();
		boolean adminLogin=false;
		if (req.getRequestURI().startsWith(req.getContextPath() + getAdminPrefix())){
			adminLogin=true;
		}
		String failureUrl = req.getParameter(FAILURE_URL);
		//验证码校验
		/*if (isCaptchaRequired(username,req, res)) {
			String captcha = request.getParameter(CAPTCHA_PARAM);
			if (captcha != null) {
				if (!imageCaptchaService.validateResponseForID(session.getSessionId(req, res), captcha)) {
					return onLoginFailure(token,failureUrl,adminLogin,new CaptchaErrorException(), request, response);
				}
			} else {
				return onLoginFailure(token,failureUrl,adminLogin,new CaptchaRequiredException(),request, response);
			}
		}*/
		/*CmsUser user=userMng.findByUsername(username);
		if(user!=null){
			if(isDisabled(user)){
				return onLoginFailure(token,failureUrl,adminLogin,new DisabledException(),request, response);
			}
			if(!isActive(user)){
				return onLoginFailure(token,failureUrl,adminLogin,new InactiveException(),request, response);
			}
		}*/
		try {
			Subject subject = getSubject(request, response);
			subject.login(token);
			return onLoginSuccess(token,adminLogin,subject, request, response);
		} catch (AuthenticationException e) {
			e.printStackTrace();
            return onLoginFailure(failureUrl,token, e, request, response);
		}
	}

	public boolean onPreHandle(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
		boolean isAllowed = isAccessAllowed(request, response, mappedValue);
		//登录跳转
		if (isAllowed && isLoginRequest(request, response)) {
			try {
				issueSuccessRedirect(request, response);
			} catch (Exception e) {
				logger.error("", e);
			}
			return false;
		}
		return isAllowed || onAccessDenied(request, response, mappedValue);
	}
	

	protected void issueSuccessRedirect(ServletRequest request, ServletResponse response)
			throws Exception {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String successUrl = req.getParameter(RETURN_URL);
		if (StringUtils.isBlank(successUrl)) {
			if (req.getRequestURI().startsWith(
					req.getContextPath() + getAdminPrefix())) {
				// 后台直接返回首页
				successUrl = getAdminIndex();
				// 清除SavedRequest
				WebUtils.getAndClearSavedRequest(request);
				WebUtils.issueRedirect(request, response, successUrl, null, true);
				return;
			} else {
				successUrl = getSuccessUrl();
			}
		}
		WebUtils.getAndClearSavedRequest(request);
		WebUtils.issueRedirect(request, response, successUrl, null, true);
		//WebUtils.redirectToSavedRequest(req, res, successUrl);
	}

	protected boolean isLoginRequest(ServletRequest req, ServletResponse resp) {
		return pathsMatch(getLoginUrl(), req)|| pathsMatch(getAdminLogin(), req);
	}

	/**
	 * 登录成功
	 */
	private boolean onLoginSuccess(AuthenticationToken token,boolean adminLogin,Subject subject,
			ServletRequest request, ServletResponse response)
			throws Exception {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String username = (String) subject.getPrincipal();
        String password = RequestUtils.getQueryParam(req,"password");
        CmsUser user = cmsUserService.getByUserName(username);
        if (password != null && user != null && MD5Util.md5Encode(password).equals(user.getUserPwd())){
            CmsRole cmsRole = cmsRoleService.getById(Long.valueOf(user.getRole()));
            user.setSuper(cmsRole.isSuper());

            HttpSession session = req.getSession();
            session.setAttribute(CmsUtil.ADMIN_USER_KEY, user);
        }

        //CmsUser user = cmsUserMng.findByUsername(username);
		//String ip = RequestUtils.getIpAddr(req);
		//Date now = new Timestamp(System.currentTimeMillis());
		//String userSessionId=session.getSessionId((HttpServletRequest)request, (HttpServletResponse)response);
		//userMng.updateLoginInfo(user.getId(), ip,now,userSessionId);
		//管理登录
		if(adminLogin){
			//cmsLogMng.loginSuccess(req, user);
		}
		//unifiedUserMng.updateLoginSuccess(user.getId(), ip);
		//loginCookie(username, req, res);
		return super.onLoginSuccess(token, subject, request, response);
	}

	/**
	 * 登录失败
	 */
	/*private boolean onLoginFailure(AuthenticationToken token,String failureUrl,boolean adminLogin,AuthenticationException e, ServletRequest request,
			ServletResponse response) {
		String username = (String) token.getPrincipal();
		HttpServletRequest req = (HttpServletRequest) request;
		//String ip = RequestUtils.getIpAddr(req);
		CmsUser user = userMng.findByUsername(username);
		if(user!=null){
			unifiedUserMng.updateLoginError(user.getId(), ip);
		}
		//管理登录
		if(adminLogin){
			//cmsLogMng.loginFailure(req,"username=" + username);
		}
		return onLoginFailure(failureUrl,token, e, request, response);
	}*/
	
	private boolean onLoginFailure(String failureUrl,AuthenticationToken token,
			AuthenticationException e, ServletRequest request,
			ServletResponse response) {
		String className = e.getClass().getName();
        request.setAttribute(getFailureKeyAttribute(), className);
        if(failureUrl!=null|| StringUtils.isNotBlank(failureUrl)){
        	try {
    			request.getRequestDispatcher(failureUrl).forward(request, response);
    		}  catch (Exception e1) {
    			//e1.printStackTrace();
    		}
        }
        return true;
	}
	


	private CmsUserService cmsUserService;
    private CmsRoleService cmsRoleService;
	
	private String adminPrefix;
	private String adminIndex;
	private String adminLogin;

	public String getAdminPrefix() {
		return adminPrefix;
	}

	public void setAdminPrefix(String adminPrefix) {
		this.adminPrefix = adminPrefix;
	}

	public String getAdminIndex() {
		return adminIndex;
	}

	public void setAdminIndex(String adminIndex) {
		this.adminIndex = adminIndex;
	}
	
	public String getAdminLogin() {
		return adminLogin;
	}

	public void setAdminLogin(String adminLogin) {
		this.adminLogin = adminLogin;
	}

    public void setCmsUserService(CmsUserService cmsUserService) {
        this.cmsUserService = cmsUserService;
    }

    public void setCmsRoleService(CmsRoleService cmsRoleService) {
        this.cmsRoleService = cmsRoleService;
    }
}
