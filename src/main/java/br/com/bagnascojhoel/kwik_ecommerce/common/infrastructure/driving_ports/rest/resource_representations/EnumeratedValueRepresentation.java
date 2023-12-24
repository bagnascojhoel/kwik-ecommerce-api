package br.com.bagnascojhoel.kwik_ecommerce.common.infrastructure.driving_ports.rest.resource_representations;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
@JsonDeserialize(builder = EnumeratedValueRepresentation.EnumeratedValueRepresentationBuilder.class)
public class EnumeratedValueRepresentation {
    private final String code;
    private final String defaultMessage;

    @JsonPOJOBuilder(withPrefix = "")
    public static class EnumeratedValueRepresentationBuilder {
    }
}
