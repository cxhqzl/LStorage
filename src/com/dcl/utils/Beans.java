package com.dcl.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * ��ȡBean
 * @author Administrator
 *
 */
@Component
public class Beans implements ApplicationContextAware {
 
    @Autowired
    private static ApplicationContext applicationContext;
 
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (Beans.applicationContext == null) {
            Beans.applicationContext = applicationContext;
        }
    }
 
    //��ȡapplicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
 
    //ͨ��name��ȡ Bean.
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }
 
    //ͨ��class��ȡBean.
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }
 
    //ͨ��name,�Լ�Clazz����ָ����Bean
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }
}