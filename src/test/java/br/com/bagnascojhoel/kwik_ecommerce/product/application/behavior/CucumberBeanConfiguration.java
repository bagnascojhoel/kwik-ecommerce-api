package br.com.bagnascojhoel.kwik_ecommerce.product.application.behavior;

import br.com.bagnascojhoel.kwik_ecommerce.product.domain.ProductRepository;
import br.com.bagnascojhoel.kwik_ecommerce.product.infrastructure.driven_ports.product.InMemoryProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

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
}
