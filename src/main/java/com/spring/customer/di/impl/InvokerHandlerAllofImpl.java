package com.spring.customer.di.impl;

import com.spring.customer.di.InvokerHandler;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;


public class InvokerHandlerAllofImpl implements InvokerHandler, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public boolean support(String type) {
        return type.equals("all");
    }

    @Override
    public void invoker(Field field, Method method, String[] serviceIds, Object... args) {
        Map<String, ?> beansOfType = applicationContext.getBeansOfType(field.getType());
        for (Map.Entry<String, ?> entry : beansOfType.entrySet()) {
            Object object = entry.getValue();
            try {
                method.invoke(object, args);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
