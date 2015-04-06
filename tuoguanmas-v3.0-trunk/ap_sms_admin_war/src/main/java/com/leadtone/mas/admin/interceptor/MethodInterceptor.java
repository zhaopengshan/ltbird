package com.leadtone.mas.admin.interceptor;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/**
 * 方法拦截器，当用户调用Action中的方法时，用于在进入方法前和退出方法后打印提示信息
 * 
 * @author licb-leadtone
 * 
 * Jan 11, 2011 9:23:47 AM
 */
public class MethodInterceptor extends MethodFilterInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger("logger");

	@Override
	protected String doIntercept(ActionInvocation ai) throws Exception {
//		logger.info("call method: " + ai.getProxy().getMethod());
		String result = ai.invoke();
//		logger.info("exit method " + ai.getProxy().getMethod());
		return result;
	}

}
