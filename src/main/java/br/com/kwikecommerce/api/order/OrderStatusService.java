package br.com.kwikecommerce.api.order;

import br.com.kwikecommerce.api.order.Order;


public interface OrderStatusService {

    void toCreatedOrder(Order order);

    void toCanceledOrder(Long orderId);

    void toDeliveredOrder(Long orderId);

}
