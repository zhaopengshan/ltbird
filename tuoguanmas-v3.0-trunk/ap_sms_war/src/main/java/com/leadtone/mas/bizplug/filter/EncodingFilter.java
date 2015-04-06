package com.leadtone.mas.bizplug.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 字符过滤器
 * 
 * @author licb-leadtone
 * 
 * Jan 10, 2011 10:31:50 AM
 */
public class EncodingFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filerChain) throws IOException, ServletException {
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		filerChain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
