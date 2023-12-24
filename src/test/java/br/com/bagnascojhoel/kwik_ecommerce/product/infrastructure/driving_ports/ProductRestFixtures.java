package br.com.bagnascojhoel.kwik_ecommerce.product.infrastructure.driving_ports;

import br.com.bagnascojhoel.kwik_ecommerce.common.infrastructure.driving_ports.rest.resource_representations.EnumeratedValueRepresentation;
import br.com.bagnascojhoel.kwik_ecommerce.product.domain.ProductState;
import br.com.bagnascojhoel.kwik_ecommerce.product.infrastructure.driving_ports.rest.ProductRepresentation;

import java.math.BigDecimal;

public final class ProductRestFixtures {
    public static final EnumeratedValueRepresentation SHOWN_PRODUCT_STATE = EnumeratedValueRepresentation.builder()
            .code(ProductState.SHOWN.getCode())
            .defaultMessage(ProductState.SHOWN.name())
            .build();

    public static final ProductRepresentation PEPERONI_PIZZA = ProductRepresentation.builder()
            .productName("Pizza Peperoni")
            .productDescription("The best pizza you'll ever eat!")
            .productState(SHOWN_PRODUCT_STATE)
            .imageUrl("https://pizza-url.com")
            .priceInBrl(new BigDecimal("49.99"))
            .build();


    public static final String PEPERONI_PIZZA_JSON_PATH = "product/rest-jsons/products/peperoni-pizza.json";

    public static final String ALL_PRODUCTS_JSON_PATH = "product/rest-jsons/products/all-products.json";
}
