package br.com.kwikecommerce.api.restcontroller.order.dto;

import br.com.kwikecommerce.api.restcontroller.ProductExists;
import br.com.kwikecommerce.api.order.PaymentMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;


@Value
public class OrderCreationRequestDto {

    // TODO jhoel.bagnasco esse campo no futuro será buscado do token de atenticação
    @NotNull
    @Schema(example = "1")
    Long companyId;

    @NotBlank
    @Schema(example = "Jonas Fonseca")
    String customerName;

    @NotBlank
    @Schema(example = "Brasil, São Paulo, São Paulo, Guarulhos, Av. Araújo Viana, 33")
    String customerAddress;

    @NotBlank
    @Schema(example = "48322888692")
    String customerPhoneNumber;

    @NotNull
    @Schema(example = "PIX")
    PaymentMethod paymentMethod;

    @NotNull
    @Schema(
        example = "56.99",
        description = "Can be different than the total sum of all OrderItems plus the freight price."
    )
    BigDecimal totalPrice;

    @Schema(example = "4.67")
    BigDecimal freightPrice;

    @NotEmpty
    @Valid
    List<OrderItem> items;

    @Value
    @Schema(name = "OrderCreationRequestDto.OrderItem")
    public static class OrderItem {

        @ProductExists
        @NotNull
        @Schema(example = "1")
        Long productId;

        @Min(1)
        @Max(999999999)
        @Schema(example = "53")
        Integer quantity;

        @DecimalMin("0")
        @DecimalMax("9999999.99")
        @Schema(example = "12.99")
        BigDecimal unitarySalePrice;

        @Schema(example = "Produto em queima de estoque.")
        String description;

    }

}
