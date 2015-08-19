package com.changyuan.misc.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component(value = "beanContainer")
public class BeanContainer implements ApplicationContextAware {

    public static ApplicationContext context;

    public static Object getBean(String beanName) {
        return context.getBean(beanName);
    }

    public static <T> T getBean(String beanName, Class<T> requiredType) {
        return context.getBean(beanName, requiredType);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    @SuppressWarnings("static-access")
    @Override
    public synchronized void setApplicationContext(ApplicationContext contextIn) throws BeansException {
        if (null == this.context) {
            this.context = contextIn;
        }
    }

}
