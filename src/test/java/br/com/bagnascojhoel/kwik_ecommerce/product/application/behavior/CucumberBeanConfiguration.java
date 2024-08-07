package br.com.bagnascojhoel.kwik_ecommerce.product.application.behavior;

import br.com.bagnascojhoel.kwik_ecommerce.product.domain.ProductRepository;
import br.com.bagnascojhoel.kwik_ecommerce.product.driven_infra.database.InMemoryProductRepository;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@AllArgsConstructor
@ComponentScan(basePackages = {
    "br.com.bagnascojhoel.kwik_ecommerce.product.domain",
    "br.com.bagnascojhoel.kwik_ecommerce.product.application"
})
public class CucumberBeanConfiguration {

  @Bean
  public ProductRepository productRepository() {
    return new InMemoryProductRepository();
  }

  @Bean
  public Validator validator() {
    return new LocalValidatorFactoryBean();
  }
}
