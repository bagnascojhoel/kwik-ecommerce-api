package br.com.kwikecommerce.api.order;

import br.com.kwikecommerce.api.order.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StatusRepository extends JpaRepository<OrderStatus, Long> {
}
