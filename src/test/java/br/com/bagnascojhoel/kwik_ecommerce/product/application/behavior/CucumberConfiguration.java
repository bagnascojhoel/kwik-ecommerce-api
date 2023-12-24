package br.com.bagnascojhoel.kwik_ecommerce.product.application.behavior;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(
        classes = CucumberBeanConfiguration.class,
        webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CucumberConfiguration {
}
