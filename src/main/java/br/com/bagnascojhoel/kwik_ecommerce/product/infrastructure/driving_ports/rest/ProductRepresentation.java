package br.com.bagnascojhoel.kwik_ecommerce.product.infrastructure.driving_ports.rest;

import br.com.bagnascojhoel.kwik_ecommerce.common.infrastructure.driving_ports.rest.resource_representations.EnumeratedValueRepresentation;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@JsonDeserialize(builder = ProductRepresentation.ProductRepresentationBuilder.class)
@Builder(toBuilder = true)
public class ProductRepresentation {
    private final String productId;

    private final EnumeratedValueRepresentation productState;

    private final String productName;

    private final String productDescription;

    private final BigDecimal priceInBrl;

    private final String imageUrl;

    @JsonPOJOBuilder(withPrefix = "")
    public static class ProductRepresentationBuilder {
    }
}
