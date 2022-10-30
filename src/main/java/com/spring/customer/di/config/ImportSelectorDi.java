package com.spring.customer.di.config;

import com.spring.customer.di.InvokerHandler;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.List;


public class ImportSelectorDi implements ImportSelector {
    
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        List<String> strings = SpringFactoriesLoader.loadFactoryNames(InvokerHandler.class, ClassUtils.getDefaultClassLoader());
        return StringUtils.toStringArray(strings);
    }

}
