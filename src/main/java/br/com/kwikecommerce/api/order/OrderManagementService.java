package br.com.kwikecommerce.api.order;

import br.com.kwikecommerce.api.company.Company;
import br.com.kwikecommerce.api.order.Order;


public interface OrderManagementService {

    Order create(Company company, Order order);

    void cancel(Long orderId);

}
