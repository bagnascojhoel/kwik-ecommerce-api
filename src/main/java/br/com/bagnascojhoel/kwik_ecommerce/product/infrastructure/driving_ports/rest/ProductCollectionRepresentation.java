package br.com.bagnascojhoel.kwik_ecommerce.product.infrastructure.driving_ports.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ProductCollectionRepresentation {
    @JsonProperty("products")
    private final List<ProductRepresentation> productRepresentations;
}
