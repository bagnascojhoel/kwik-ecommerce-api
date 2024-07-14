package br.com.bagnascojhoel.kwik_ecommerce.product.driving_infra.rest;

import br.com.bagnascojhoel.kwik_ecommerce.common.domain.AbstractResourceNotFoundException;
import br.com.bagnascojhoel.kwik_ecommerce.common.driving_infra.rest.ErrorDto;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface RestExceptionHandlerApi {

  @ApiResponse(
      responseCode = "400",
      description = "Bad Request",
      content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorDto.class)
      )
  )
  ErrorDto handleBadRequest(
      final HttpServletRequest httpServletRequest,
      final Throwable throwable
  );

  @ApiResponse(
      responseCode = "404",
      description = "Not Found",
      content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorDto.class)
      )
  )
  ErrorDto handleResourceNotFound(
      final HttpServletRequest httpServletRequest,
      final AbstractResourceNotFoundException abstractResourceNotFoundException);

  @ApiResponse(
      responseCode = "500",
      description = "Internal Server Error",
      content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorDto.class)
      )
  )
  ErrorDto handleInternalServerError(
      final HttpServletRequest httpServletRequest,
      final Throwable throwable);

}
