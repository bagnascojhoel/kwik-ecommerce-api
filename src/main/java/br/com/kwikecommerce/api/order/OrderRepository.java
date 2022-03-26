package br.com.kwikecommerce.api.order;

import br.com.kwikecommerce.api.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, Long> {
}
