package br.com.kwikecommerce.api.application;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


@Component
public class SpringContext implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static <T extends Object> T getBean(Class<T> beanClass) {
        return applicationContext.getBean(beanClass);
    }

    @Override
    public void setApplicationContext(ApplicationContext anApplicationContext) throws BeansException {
        applicationContext = anApplicationContext;
    }

}
