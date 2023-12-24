package br.com.bagnascojhoel.kwik_ecommerce.product.domain;

import br.com.bagnascojhoel.kwik_ecommerce.common.domain.AbstractResourceNotFoundException;

public class ProductNotFoundException extends AbstractResourceNotFoundException {
    public ProductNotFoundException() {
        super("The product was not found", "product-not-found");
    }
}
