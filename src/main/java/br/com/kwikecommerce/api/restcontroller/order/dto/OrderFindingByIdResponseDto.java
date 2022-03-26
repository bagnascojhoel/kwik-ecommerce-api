package br.com.kwikecommerce.api.restcontroller.order.dto;

import br.com.kwikecommerce.api.order.PaymentMethod;
import br.com.kwikecommerce.api.order.OrderStatusType;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Value
@Builder
public class OrderFindingByIdResponseDto {

    String customerName;
    String customerAddress;
    String customerPhoneNumber;
    BigDecimal totalPrice;
    PaymentMethod paymentMethod;
    OrderStatus status;
    List<OrderItem> items;

    @Value
    @Builder
    public static class OrderStatus {

        OrderStatusType type;
        String reason;
        LocalDateTime changedAt;

    }

    @Value
    @Builder
    public static class OrderItem {

        Long id;
        Integer quantity;
        BigDecimal unitarySalePrice;
        String description;

    }

}
