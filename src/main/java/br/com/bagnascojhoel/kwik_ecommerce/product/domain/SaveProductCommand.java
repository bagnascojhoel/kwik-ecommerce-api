package br.com.bagnascojhoel.kwik_ecommerce.product.domain;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Builder(toBuilder = true)
@Nullable
public class SaveProductCommand {
    private final ProductId id;
    
    private final String name;

    private final String description;

    private final BigDecimal priceInBrl;

    private final String imageUrl;
}
