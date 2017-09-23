package com.fighter.ace.framework.web.springmvc;

import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

public class ServletContextRealPathResolver implements RealPathResolver,
		ServletContextAware {
	public String get(String path) {
		String realpath=context.getRealPath(path);
		//tomcat8.0获取不到真实路径，通过/获取路径
		if(realpath==null){
			realpath=context.getRealPath("/")+path;
		}
		return realpath;
	}

	public void setServletContext(ServletContext servletContext) {
		this.context = servletContext;
	}

	private ServletContext context;
}
