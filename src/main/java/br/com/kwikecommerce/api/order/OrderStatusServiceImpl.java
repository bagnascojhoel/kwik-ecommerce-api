package br.com.kwikecommerce.api.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class OrderStatusServiceImpl implements OrderStatusService {

    private final OrderStatusRepository orderStatusRepostiory;

    // TODO jhoel.bagnasco 01/08/2021 | Ver como criar um repository independente de JPA para fazer queries de status

    @Override
    public void toCreatedOrder(Order order) {
        orderStatusRepostiory.toCreatedOrder(order);
    }

    @Override
    public void toCanceledOrder(Long orderId) {

    }

    @Override
    public void toDeliveredOrder(Long orderId) {

    }

}
