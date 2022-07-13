package com.info.common.util;

import javax.servlet.ServletContext;
import org.springframework.web.context.WebApplicationContext;
/**
 *
 * 绑定应用上下文，以供应用其它地方使用。<br/>
 * @version 1.0
 */
public class SpringHelper {

	private static ServletContext servletContext=null;
	
	public static void initServletContext(ServletContext servletContext){
		if(SpringHelper.servletContext==null){
			SpringHelper.servletContext=servletContext;
		}
	}
	
	public static Object getBean(String beanName){
		WebApplicationContext context=(WebApplicationContext)servletContext.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		return context.getBean(beanName);
	}
	
	public static ServletContext getServletContext(){
		return SpringHelper.servletContext;
	}
}
