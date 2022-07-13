package com.info.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter {
	
	public static String[] urls = new String[]
			{"login.jsp","login.do","logout.do","register.jsp","logout.do"};

	public void destroy() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		 // 获得在下面代码中要用的request,response,session对象
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		HttpServletResponse servletResponse = (HttpServletResponse) response;
		HttpSession session = servletRequest.getSession();
		
		// 获得用户请求的URI
		String path = servletRequest.getRequestURI();
		
		for (int i = 0,l = urls.length; i < l; i++) {
			String url = urls[i];
			if(path.indexOf(url) > -1){
				chain.doFilter(servletRequest, servletResponse);
	            return;
			}
		}
		
		String role = (String)session.getAttribute("role");
		
		// 判断如果没有取到员工信息,就跳转到登陆页面
		if (role == null || "".equals(role)) {
			// 跳转到登陆页面
			String baseURL=servletRequest.getScheme()+"://"+servletRequest.getServerName()+":"+servletRequest.getServerPort()+ servletRequest.getContextPath();
			servletResponse.sendRedirect( baseURL + "/login.jsp");
		} else {
			// 已经登陆,继续此次请求
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		
	}
	
}
