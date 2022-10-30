package com.spring.customer.di;

import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

public class DiProxy {


    public static Object proxyInstance(Field field, ApplicationContext context) {
        return Proxy.newProxyInstance(ClassUtils.getDefaultClassLoader(), new Class[]{field.getType()}, new InvokeAdvice(field, context));
    }

    private static class InvokeAdvice implements InvocationHandler {

        private Field field;

        private ApplicationContext applicationContext;

        public InvokeAdvice(Field field, ApplicationContext applicationContext) {
            this.field = field;
            this.applicationContext = applicationContext;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Map<String, InvokerHandler> beansOfType = applicationContext.getBeansOfType(InvokerHandler.class);
            AnnotationAttributes annotationAttributes = AnnotatedElementUtils.getMergedAnnotationAttributes(field, DiAnnotation.class);
            String diVal = annotationAttributes.getString("type");
            String[] serviceIds = annotationAttributes.getStringArray("serviceIds");
            for (Map.Entry<String, InvokerHandler> invokerHandlerEntry : beansOfType.entrySet()) {
                InvokerHandler handler = invokerHandlerEntry.getValue();
                if (handler.support(diVal)) {
                    handler.invoker(field, method, serviceIds, args);
                }
            }
            return null;
        }


    }
}
