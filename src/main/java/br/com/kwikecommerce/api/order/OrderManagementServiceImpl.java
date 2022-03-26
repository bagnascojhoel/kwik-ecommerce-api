package br.com.kwikecommerce.api.order;

import br.com.kwikecommerce.api.company.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class OrderManagementServiceImpl implements OrderManagementService {

    private final OrderService orderService;
    private final OrderStatusService orderStatusService;
    private final OrderItemMapper orderItemMapper;

    @Override
    public Order create(Company company, Order order) {
        var createdOrder = orderService.create(company, order);
        orderStatusService.toCreatedOrder(createdOrder);

        return createdOrder;
    }

    @Override
    public void cancel(Long orderId) {

    }

}
