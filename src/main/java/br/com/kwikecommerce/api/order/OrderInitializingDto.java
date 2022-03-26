package br.com.kwikecommerce.api.order;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;


@Value
@Builder
public class OrderInitializingDto {
    String customerName;
    String customerAddress;
    String customerPhoneNumber;
    PaymentMethod paymentMethod;
    BigDecimal totalPrice;
    BigDecimal freightPrice;
    List<OrderItem> items;
}
