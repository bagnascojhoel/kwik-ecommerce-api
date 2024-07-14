package br.com.bagnascojhoel.kwik_ecommerce.common.driving_infra.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Builder
@Getter
@JsonPropertyOrder({"typeUri", "title"})
public class ErrorDto {

  @Schema(name = "type",
      example = "/errors/server-errors/internal-server-error",
      examples = {
          "/errors/server-errors/internal-server-error",
          "/errors/client-errors/not-founds",
          "/errors/client-errors/bad-requests/failed-validation"
      })
  @JsonProperty("type")
  private final String typeUri;

  @Schema(example = "Something on our side went wrong")
  private final String title;

  @Schema(nullable = true)
  private final List<FailedValidationDto> failedValidations;
}
