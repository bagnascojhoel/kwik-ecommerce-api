package br.com.bagnascojhoel.kwik_ecommerce.product.domain;

import java.math.BigDecimal;

public final class ProductDomainFixtures {
    public static final SaveProductCommand SAVE_PEPERONI_PIZZA = SaveProductCommand.builder()
            .name("Pizza Peperoni")
            .description("The best pizza you'll ever eat!")
            .imageUrl("https://pizza-url.com")
            .priceInBrl(new BigDecimal("49.99"))
            .build();

    public static final Product PEPERONI_PIZZA = Product.builder()
            .name("Pizza Peperoni")
            .description("The best pizza you'll ever eat!")
            .state(ProductState.SHOWN)
            .imageUrl("https://pizza-url.com")
            .priceInBrl(new BigDecimal("49.99"))
            .build();

    public static final Product DOUBLE_CHEESE_BURGUER = Product.builder()
            .name("Double-Chesse Burguer")
            .description("The best hamburguer you'll ever eat!")
            .state(ProductState.HIDDEN)
            .imageUrl("https://hamburguer-url.com")
            .priceInBrl(new BigDecimal("29.99"))
            .build();
}
