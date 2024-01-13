package br.com.bagnascojhoel.kwik_ecommerce.common.driving_infra.rest;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Builder
public class FailedValidationDto {

    private final String field;
    private final String reason;

}
