package br.com.bagnascojhoel.kwik_ecommerce.common.driving_infra.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;


@RequiredArgsConstructor
@Builder
@Getter
@JsonPropertyOrder({"typeUri", "title"})
public class ErrorDto {
    @JsonProperty("type")
    private final String typeUri;

    private final String title;

    private final List<FailedValidationDto> failedValidations;
}
