package br.com.kwikecommerce.api.order;

import br.com.kwikecommerce.api.application.pagination.PageResponseDto;
import br.com.kwikecommerce.api.application.exceptionhandling.NotFoundException;
import br.com.kwikecommerce.api.application.pagination.PaginationMapper;
import br.com.kwikecommerce.api.application.database.ProbeUtil;
import br.com.kwikecommerce.api.restcontroller.order.dto.OrderFindingByFilterResponse;
import br.com.kwikecommerce.api.restcontroller.order.dto.OrderFindingByIdResponseDto;
import br.com.kwikecommerce.api.company.Company;
import br.com.kwikecommerce.api.application.message.MessageProperty;
import br.com.kwikecommerce.api.application.pagination.PageRequest;
import br.com.kwikecommerce.api.application.pagination.sort.NoSortOption;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final PaginationMapper paginationMapper;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public Order create(Company company, Order order) {
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order update(Long orderId, PaymentMethod paymentMethod, BigDecimal freightPrice) {
        var oldOrder = orderRepository.findById(orderId)
            .orElseThrow(() -> new NotFoundException(MessageProperty.of("e.order.not-found")));

        var newOrder = orderMapper.toOrder(oldOrder, paymentMethod, freightPrice);

        return orderRepository.save(newOrder);
    }

    @Override
    public OrderFindingByIdResponseDto findById(Long orderId) {
        var probe = ((Order.OrderBuilder) ProbeUtil.builderFor(Order.class))
            .id(orderId)
            .build();
        var order = this.mustFind((Order) probe);

        return orderMapper.toFindingByIdResponse(order);
    }

    // TODO jhoel.bagnasco 07/08/2021 | Atualizar para esperar um objeto para filtragem
    @Override
    public PageResponseDto<OrderFindingByFilterResponse> findByFilter(PageRequest<NoSortOption> pageRequest) {
        var page = orderRepository.findAll(pageRequest)
            .map(orderMapper::toFindingByFilterResponse);

        return paginationMapper.map(page);
    }

    private Order mustFind(Order probe) {
        var example = Example.of(probe);
        return orderRepository.findOne(example)
            .orElseThrow(() -> new NotFoundException(MessageProperty.of("e.order.not-found")));
    }

}
