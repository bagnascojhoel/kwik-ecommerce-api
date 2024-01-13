package br.com.bagnascojhoel.kwik_ecommerce.common.driving_infra.rest;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
@JsonDeserialize(builder = EnumeratedValueDto.EnumeratedValueDtoBuilder.class)
public class EnumeratedValueDto {
    private final String code;
    private final String label;

    @JsonPOJOBuilder(withPrefix = "")
    public static class EnumeratedValueDtoBuilder {
    }
}
