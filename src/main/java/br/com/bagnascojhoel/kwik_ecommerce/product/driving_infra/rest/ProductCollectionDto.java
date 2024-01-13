package br.com.bagnascojhoel.kwik_ecommerce.product.driving_infra.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ProductCollectionDto {
    @JsonProperty("products")
    private final List<ProductDto> productDtos;
}
