package com.leadtone.mas.bizplug.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginCheckedFilter implements Filter {
    private static final String LOGINADMIN="loginAdmin";
    @Override
    public void doFilter(ServletRequest servlerequest, ServletResponse servleresponse,
            FilterChain chain) throws IOException, ServletException {
//        //获取request对象
//        HttpServletRequest request = (HttpServletRequest) servlerequest;
//        HttpServletResponse response = (HttpServletResponse) servleresponse;
//        String requestURL = request.getRequestURL().toString();
//        if(requestURL.indexOf("login.action") != -1 || requestURL.indexOf("login.jsp") != -1 || (requestURL.indexOf(".jsp")==-1 && requestURL.indexOf(".action")==-1)) {
//            chain.doFilter(servlerequest, servleresponse);
//        } else {
//            Object sessionObject = request.getSession().getAttribute(LOGINADMIN);            
//            if(sessionObject == null) {
//                String timeoutRedirect=new StringBuffer(request.getScheme()).append("://").append(request.getServerName()).append(":").append(request.getServerPort()).append(request.getContextPath()).toString();
//                response.sendRedirect(timeoutRedirect);
//                return;
//            }
            chain.doFilter(servlerequest, servleresponse);
//        }        
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
