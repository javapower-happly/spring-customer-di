package com.spring.customer.di.config;

import com.spring.customer.di.DiAnnotationBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Import(ImportSelectorDi.class)
public class AutoConfig {

    @Bean
    public DiAnnotationBeanPostProcessor diAnnotationBeanPostProcessor() {
        return new DiAnnotationBeanPostProcessor();
    }

}
