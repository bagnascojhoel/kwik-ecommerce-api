package br.com.kwikecommerce.api.restcontroller.product.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;


@Builder
@Data
@AllArgsConstructor
public class ProductListingResponse {

    private String title;
    private BigDecimal unitaryPrice;
    private Integer availableQty;
    private String details;
    private Set<Long> categoriesIds;
    private List<String> imagesUrls;

}
