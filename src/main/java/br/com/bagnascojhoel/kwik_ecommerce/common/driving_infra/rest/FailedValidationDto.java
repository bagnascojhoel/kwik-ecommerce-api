package br.com.bagnascojhoel.kwik_ecommerce.common.driving_infra.rest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Builder
public class FailedValidationDto {

  @Schema(examples = {"productName", "client.address[0]"})
  private final String field;

  @Schema(example = "Exceeded maximum of 20 characters")
  private final String reason;
  
}
