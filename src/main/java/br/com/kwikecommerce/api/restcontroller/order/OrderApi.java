package br.com.kwikecommerce.api.restcontroller.order;

import br.com.kwikecommerce.api.application.pagination.PageRequest;
import br.com.kwikecommerce.api.application.pagination.PageResponseDto;
import br.com.kwikecommerce.api.application.pagination.sort.NoSortOption;
import br.com.kwikecommerce.api.restcontroller.OrderExists;
import br.com.kwikecommerce.api.restcontroller.order.dto.OrderCreationRequestDto;
import br.com.kwikecommerce.api.restcontroller.order.dto.OrderFindingByFilterResponse;
import br.com.kwikecommerce.api.restcontroller.order.dto.OrderFindingByIdResponseDto;
import br.com.kwikecommerce.api.restcontroller.order.dto.OrderUpdateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Tag(
    name = "Orders",
    description = "Operations over order resources"
)
public interface OrderApi {

    @Operation(summary = "Initiates an order with its items")
    Long init(@NotNull Long companyId, @Valid OrderCreationRequestDto orderCreationRequestDto);

    @Operation(summary = "Updates an order")
    void update(@OrderExists Long orderId, @Valid OrderUpdateRequestDto orderUpdateRequestDto);

    void cancel(Long orderId);

    @Operation(summary = "Finds orders valid for given filter")
    PageResponseDto<OrderFindingByFilterResponse> findByFilter(PageRequest<NoSortOption> pageRequest);

    @Operation(summary = "Finds an order by its ID")
    OrderFindingByIdResponseDto findById(@OrderExists Long orderId);

}
