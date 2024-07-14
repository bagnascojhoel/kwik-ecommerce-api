package br.com.bagnascojhoel.kwik_ecommerce.common.driving_infra.rest;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@JsonDeserialize(builder = EnumeratedValueDto.EnumeratedValueDtoBuilder.class)
public class EnumeratedValueDto {

  private final String code;
  @Getter
  private final String label;

  public String getCode() {
    return this.code.toUpperCase();
  }

  @JsonPOJOBuilder(withPrefix = "")
  public static class EnumeratedValueDtoBuilder {

  }
}
