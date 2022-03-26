package br.com.kwikecommerce.api.order;

import br.com.kwikecommerce.api.order.Order;


public interface OrderStatusRepository {

    void toCreatedOrder(Order order);

    void toCanceledOrder(Order order, String reason);

}
