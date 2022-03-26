package br.com.kwikecommerce.api.order;

import br.com.kwikecommerce.api.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
