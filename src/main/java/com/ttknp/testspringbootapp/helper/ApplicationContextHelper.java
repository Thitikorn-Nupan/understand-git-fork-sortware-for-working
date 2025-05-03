package com.ttknp.testspringbootapp.helper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@Slf4j
public class ApplicationContextHelper implements ApplicationContextAware {

    private  ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    // how to get table by bean class
    public String getTableNameByBean(String beanName) {
        Object contextBean = applicationContext.getBean(beanName);
        String serviceName = contextBean.getClass().getSimpleName();
        String tableName = serviceName.substring(0,serviceName.indexOf("Service"));
        tableName = tableName.toLowerCase();
        tableName += "s";
        // assume all services name start with the same name of table
        return tableName;
    }

    public String getTableNameByBean(Class<?> beanClass) {
        Object contextBean = applicationContext.getBean(beanClass);
        String serviceName = contextBean.getClass().getSimpleName();
        String tableName = serviceName.substring(0,serviceName.indexOf("Service"));
        tableName = tableName.toLowerCase();
        tableName += "s";
        return tableName;
    }

}
