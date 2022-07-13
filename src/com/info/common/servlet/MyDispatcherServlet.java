package com.info.common.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.DispatcherServlet;

import com.info.view.log.model.Log;
import com.info.view.log.service.ILogService;
import com.info.view.log.service.impl.LogServiceImpl;

/**
 * 重写SpringMVC-DispatcherServlet（默认只能解决post提交的编码问题）
 *       只为解决SpringMVC的编码问题
 * @author liuyuehu
 * @date   2016-3-10
 */
public class MyDispatcherServlet extends DispatcherServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -752326141619697682L;
	
	/**
	 * 编码
	 */
	private String encoding;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		encoding = config.getInitParameter("encoding");
		super.init(config);
	}

	@Override
	protected void doService(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding(encoding);
		response.setCharacterEncoding(encoding);
		super.doService(request, response);
	}
}
