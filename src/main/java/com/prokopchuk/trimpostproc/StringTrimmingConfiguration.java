package com.prokopchuk.trimpostproc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StringTrimmingConfiguration {
    @Bean
    public TrimmedAnnotationBeanPostProcessor trimmedAnnotationBeanPostProcessor() {
        return new TrimmedAnnotationBeanPostProcessor();
    }
}
