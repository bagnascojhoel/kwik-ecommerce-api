package br.com.kwikecommerce.api.restcontroller.order.dto;

import br.com.kwikecommerce.api.order.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;


@Value
@Builder
public class OrderUpdateRequestDto {
    // TODO jhoel.bagnasco 24/08/2021 | Adicionar status

    @JsonIgnore
    @Schema(example = "CORREIOS")
    String freightType;

    @Schema(example = "12.5")
    BigDecimal freightPrice;

    @Schema(example = "CREDIT_CARD")
    PaymentMethod paymentMethod;

}
