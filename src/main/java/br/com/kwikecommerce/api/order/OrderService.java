package br.com.kwikecommerce.api.order;

import br.com.kwikecommerce.api.application.pagination.PageResponseDto;
import br.com.kwikecommerce.api.restcontroller.order.dto.OrderFindingByFilterResponse;
import br.com.kwikecommerce.api.restcontroller.order.dto.OrderFindingByIdResponseDto;
import br.com.kwikecommerce.api.company.Company;
import br.com.kwikecommerce.api.application.pagination.PageRequest;
import br.com.kwikecommerce.api.application.pagination.sort.NoSortOption;

import java.math.BigDecimal;


public interface OrderService {

    Order create(Company company, Order order);

    Order update(Long orderId, PaymentMethod paymentMethod, BigDecimal freightPrice);

    PageResponseDto<OrderFindingByFilterResponse> findByFilter(PageRequest<NoSortOption> pageRequest);

    OrderFindingByIdResponseDto findById(Long orderId);

}
