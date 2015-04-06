package com.leadtone.mas.admin.filter;

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

import org.apache.log4j.Logger;

//import sun.security.jgss.LoginConfigImpl;

/**
 * 用户登录权限过滤器
 */
public class PermitFilter implements Filter {
	public static Logger logger = Logger.getLogger(PermitFilter.class);
	/**
	 * 过滤字符串,默认为GBK
	 */
	protected String encoding = "";
	protected FilterConfig filterConfig = null;
	/**
	 * 不被过滤的路径
	 */
	protected String[] antiFiltered;

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		this.encoding = filterConfig.getInitParameter("encoding");
		String tmp = filterConfig.getInitParameter("antiFiltered");
		if (tmp != null)
			this.antiFiltered = tmp.split(",");
		else
			this.antiFiltered = new String[] {};

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// 验证权限
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		String servletPath = req.getServletPath();
		servletPath = servletPath.replaceAll("//", "/");
		//TODO 去掉权限验证
		session.setAttribute("user", 1);
		// 解决session跨域丢失的问题
		((HttpServletResponse) response).setHeader("P3P", "CP=CAO PSA OUR");
		if (session.getAttribute("user") == null) {
			// 是否是应被过滤的路径
			boolean filtered = true;
			for (String str : antiFiltered) {
				if (servletPath.startsWith(str)) {
					filtered = false;
					break;
				}
			}
			if (filtered) {
				logger.warn("filtered the request : " + req.getContextPath()
						+ req.getServletPath());
				((HttpServletResponse) response).sendRedirect(req
						.getContextPath()
						+ "/session_timeout.jsp");
				return;
			}
		}
		// 字符集过滤
		// request.setCharacterEncoding(this.encoding);

		chain.doFilter(request, response);
	}

	public void destroy() {
		this.encoding = null;
		this.filterConfig = null;
	}
}