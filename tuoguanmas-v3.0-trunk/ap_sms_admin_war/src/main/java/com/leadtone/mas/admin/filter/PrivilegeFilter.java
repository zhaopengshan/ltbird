/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leadtone.mas.admin.filter;

import com.leadtone.mas.bizplug.common.ApSmsConstants;
import com.leadtone.mas.bizplug.security.bean.Resources;
import com.leadtone.mas.bizplug.util.WebUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author blueskybluesea
 */
public class PrivilegeFilter implements Filter {

    @Override
    public void init(FilterConfig fc) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servlerequest, ServletResponse servleresponse, FilterChain chain) throws IOException, ServletException {
        //获取request对象
        HttpServletRequest request = (HttpServletRequest) servlerequest;
        HttpServletResponse response = (HttpServletResponse) servleresponse;
        String requestURL = request.getRequestURL().toString();
        boolean isOnline = false;
		if(!org.apache.commons.lang3.StringUtils.isBlank(WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.ONLINE))){
			isOnline = Boolean.valueOf(WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.ONLINE));                   
        }else{
        	isOnline = false;
        }
        if (requestURL.indexOf("login.action") != -1 
                || requestURL.indexOf("login.jsp") != -1 
                || requestURL.indexOf("welcome.jsp") != -1
                || requestURL.indexOf("smslogin_ford.jsp") != -1
                //首次登陆限制
                || requestURL.indexOf("smsCheckLoginGet.action") != -1
                || requestURL.indexOf("loginSmsCheck.action") != -1
        		|| requestURL.indexOf("firstLoginPwd.jsp") != -1
             	|| requestURL.indexOf("firstloginUser.action") != -1
             	|| requestURL.indexOf("firstuserpwd.jsp") != -1
             	|| requestURL.indexOf("validatePwd.action") != -1
             	|| requestURL.indexOf("updatePwd.action") != -1
             	|| requestURL.indexOf("updatePwdFirst.action") != -1
             	|| requestURL.indexOf("adminFirstloginUser.action") != -1
             	|| requestURL.indexOf("adminFirstLoginPwd.jsp") != -1
             	|| requestURL.indexOf("adminFirstuserpwd.jsp") != -1
             	//单机版
                || (!isOnline && (requestURL.indexOf("register.jsp") != -1 || requestURL.indexOf("register.action") != -1
                || requestURL.indexOf("offlineRegister.action") != -1
                || requestURL.indexOf("onlineRegister.action") != -1
                || requestURL.indexOf("registerOnline.jsp") != -1|| requestURL.indexOf("registerOffline.jsp") != -1))
                //找回密码 
                || requestURL.indexOf("findPwd.jsp") != -1
                || requestURL.indexOf("userChelcking.action") != -1
                || requestURL.indexOf("sendPwd.action") != -1
                //
                || requestURL.indexOf("verifyCode.action") != -1
                || requestURL.indexOf("checkingNumber.action") != -1
                || (requestURL.indexOf(".jsp") == -1 && requestURL.indexOf(".action") == -1)) {
            chain.doFilter(servlerequest, servleresponse);
        } else {
            Object userObject = request.getSession().getAttribute(ApSmsConstants.SESSION_USER_INFO);
            List<Resources> resources = (ArrayList<Resources>) request.getSession().getAttribute("resources");
            if (userObject == null || resources == null || resources.size() <= 0) {
                String timeoutRedirect = new StringBuffer(request.getScheme()).append("://").append(request.getServerName()).append(":").append(request.getServerPort()).append(request.getContextPath()).toString();
                //response.sendRedirect(timeoutRedirect);
                response.getWriter().write("<script type='text/javascript'>window.location.href='"+timeoutRedirect+"'</script>");
                return;
            }
            boolean isPermitAccess = false;
            String[] accessUrls = requestURL.split("/");
            String accessPath = accessUrls[accessUrls.length - 2];
            String delRequestActionPath = accessPath.indexOf("Action") != -1 ? accessPath.substring(0, accessPath.indexOf("Action") - 1) : "";
            String delRequestactionPath = accessPath.indexOf("action") != -1 ? accessPath.substring(0, accessPath.indexOf("action") - 1) : "";
            for (Resources resource : resources) {
                Set<Resources> subResources = resource.getSubResources();
                for (Resources subResource : subResources) {
                    String[] privilegeUrls = subResource.getUrl().split("/");
                    //待优化
                    String privilegePath = privilegeUrls[privilegeUrls.length - 2];
                    String delActionPath = privilegePath.indexOf("Action") != -1 ? privilegePath.substring(0, privilegePath.indexOf("Action") - 1) : "";
                    String delactionPath = privilegePath.indexOf("action") != -1 ? privilegePath.substring(0, privilegePath.indexOf("action") - 1) : "";                    
                    if (StringUtils.equals(privilegePath, accessPath) 
                            || StringUtils.endsWithIgnoreCase(delActionPath, accessPath) 
                            || StringUtils.endsWithIgnoreCase(delactionPath, delactionPath)
                            || StringUtils.endsWithIgnoreCase(delRequestActionPath, privilegePath)
                            || StringUtils.endsWithIgnoreCase(delRequestactionPath, privilegePath)) {
                        isPermitAccess = true;
                        break;
                    }
                }
                if (isPermitAccess) {
                    break;
                }
            }
            if (!isPermitAccess && requestURL.indexOf("main.jsp") == -1) {
                String timeoutRedirect = new StringBuffer(request.getScheme()).append("://").append(request.getServerName()).append(":").append(request.getServerPort()).append(request.getContextPath()).toString();
                //response.sendRedirect(timeoutRedirect);
                response.getWriter().write("<script type='text/javascript'>window.location.href='"+timeoutRedirect+"'</script>");
                return;
            }
            chain.doFilter(servlerequest, servleresponse);
        }
    }

    @Override
    public void destroy() {
    }
}
