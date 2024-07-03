package com.algaworks.algafood.core.validation;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class ValidationConfig {

    @Bean
    public LocalValidatorFactoryBean validator(MessageSource messageSource) {
        var bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource);
        return bean;
    }
}
