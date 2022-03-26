package br.com.kwikecommerce.api.restcontroller.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ProductUpdateRequest {

    private Long id;
    private String title;
    private BigDecimal unitaryPrice;
    private Integer availableQty;
    private String description;

}
