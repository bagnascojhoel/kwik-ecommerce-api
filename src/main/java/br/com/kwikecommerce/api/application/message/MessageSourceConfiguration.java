package br.com.kwikecommerce.api.application.message;

import br.com.kwikecommerce.api.application.Constants;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.nio.charset.StandardCharsets;


@Configuration
public class MessageSourceConfiguration {

    private static final String[] MESSAGE_SOURCE_FILES = {
        "messages",
        "messages-log",
        "messages-exception"
    };

    @Bean
    public LocalValidatorFactoryBean validator() {
        var validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(messageSource());
        return validator;
    }

    @Bean
    public MessageSource messageSource() {
        var messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames(MESSAGE_SOURCE_FILES);
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.displayName());
        messageSource.setFallbackToSystemLocale(false);
        return messageSource;
    }

    @Bean
    public LocaleResolver localeResolver() {
        var localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Constants.LOCALE_PT_BR);
        return localeResolver;
    }

}
