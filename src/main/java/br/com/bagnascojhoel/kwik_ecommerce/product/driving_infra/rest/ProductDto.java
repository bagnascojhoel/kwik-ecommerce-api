package br.com.bagnascojhoel.kwik_ecommerce.product.driving_infra.rest;

import br.com.bagnascojhoel.kwik_ecommerce.common.driving_infra.rest.EnumeratedValueDto;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@JsonDeserialize(builder = ProductDto.ProductDtoBuilder.class)
@Builder(toBuilder = true)
public class ProductDto {
    private final String productId;

    private final EnumeratedValueDto productState;

    private final String productName;

    private final String productDescription;

    private final BigDecimal priceInBrl;

    private final String imageUrl;

    @JsonPOJOBuilder(withPrefix = "")
    public static class ProductDtoBuilder {
    }
}



