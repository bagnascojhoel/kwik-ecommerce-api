package br.com.kwikecommerce.api.restcontroller.domaintypes;

import br.com.kwikecommerce.api.restcontroller.domaintypes.dto.DomainTypeListingResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Set;


@Tag(
    name = "DomainTypes",
    description = "Listing operations for domain types"
)
public interface DomainTypesApi {

    @Operation(summary = "Finds all possible order status types")
    Set<DomainTypeListingResponseDto> findOrderStatusTypes();

    @Operation(summary = "Finds all possible payment methods")
    Set<DomainTypeListingResponseDto> findPaymentMethods();

}
