package br.com.kwikecommerce.api.restcontroller.domaintypes.dto;

import lombok.Builder;
import lombok.Value;


@Builder
@Value
public class DomainTypeListingResponseDto {

    String name;
    String description;

}
