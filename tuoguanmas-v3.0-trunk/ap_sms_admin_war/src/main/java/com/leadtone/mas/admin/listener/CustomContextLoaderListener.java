package com.leadtone.mas.admin.listener;

import javax.servlet.ServletContextEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;

import cn.j2eebestpractice.ssiframework.util.SpringUtil;


/**
 * 
 * @author hejiyong
 * date:2013-1-23
 * 
 */
public class CustomContextLoaderListener extends ContextLoaderListener{

	private static final Logger logger = LoggerFactory.getLogger(CustomContextLoaderListener.class);

	@Override
	public void contextInitialized(ServletContextEvent event) {
		// TODO Auto-generated method stub
		try {
			super.contextInitialized(event);
			ApplicationContext ctx = SpringUtil.getWebApplicationContext(event.getServletContext());
			SpringUtil.setApplicationContext(ctx);
		} catch (Exception ex) {
			logger.error("", ex);
		}
	}
}
