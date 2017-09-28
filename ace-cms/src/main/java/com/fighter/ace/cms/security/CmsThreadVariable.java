package com.fighter.ace.cms.security;

import com.fighter.ace.cms.entity.main.CmsUser;

/**
 * CMS线程变量
 */
public class CmsThreadVariable {
	/**
	 * 当前用户线程变量
	 */
	private static ThreadLocal<CmsUser> cmsUserVariable = new ThreadLocal<CmsUser>();

	/**
	 * 获得当前用户
	 * 
	 * @return
	 */
	public static CmsUser getUser() {
		return cmsUserVariable.get();
	}

	/**
	 * 设置当前用户
	 * 
	 * @param user
	 */
	public static void setUser(CmsUser user) {
		cmsUserVariable.set(user);
	}

	/**
	 * 移除当前用户
	 */
	public static void removeUser() {
		cmsUserVariable.remove();
	}

}
