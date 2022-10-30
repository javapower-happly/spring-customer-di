package com.spring.customer.di;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public interface InvokerHandler {

    boolean support(String type);

    void invoker(Field field, Method method, String[] serviceIds,Object... args);

}
