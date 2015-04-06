package com.leadtone.mas.bizplug.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * Action拦截器，用于在用户调用Action前和退出Action后打印日志消息
 * 
 * @author licb-leadtone
 * 
 *         Jan 10, 2011 1:47:32 PM
 */
public class ActionInterceptor extends AbstractInterceptor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger("logger");

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		super.init();
	}

	@SuppressWarnings("unchecked")
	@Override
	public String intercept(ActionInvocation ai) throws Exception {
		// logger.info("enter into action: " + ai.getProxy().getActionName()
		// + ", " + ai.getAction().getClass());
		ActionContext act = ai.getInvocationContext();
		Map session = act.getSession();
		//UserBean user = null; 
			//session.get("user") == null ? null : (UserBean) session
			//	.get("user");
		Object user = null;
		/**
		 * 如果用户没有登录，那么执行登录的操作
		 */
		if (user == null) {
			act.put("message", "会话过期,请重新登录!");

			session.put("logoutReason", "timeout");
			return Action.LOGIN;
		}

		/**
		 * 过滤一下request和response中的字符编码
		 */
		HttpServletRequest request = (HttpServletRequest) act
				.get(ServletActionContext.HTTP_REQUEST);
		HttpServletResponse reponse = (HttpServletResponse) act
				.get(ServletActionContext.HTTP_RESPONSE);
		request.setCharacterEncoding("UTF-8");
		reponse.setContentType("text/html; charset=UTF-8");
		reponse.setHeader("Cache-Control", "no-cache");
		reponse.setHeader("Charset", "UTF-8");
		//reponse.setCharacterEncoding("UTF-8");

		// 这里之前的内容在调用Action之前执行
		String result = ai.invoke();
		// 这里之后的内容在调用Action之后执行
		// logger.info("exit action: " + ai.getProxy().getActionName() + ", "
		// + ai.getAction().getClass());
		return result;
	}
}
