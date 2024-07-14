package br.com.bagnascojhoel.kwik_ecommerce.product.domain;

import jakarta.annotation.Nullable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder(toBuilder = true)
@Nullable
@EqualsAndHashCode
public class SaveProductCommand {

  private final String name;

  private final String description;

  private final BigDecimal priceInBrl;

  private final String imageUrl;

  private final ProductState productState;

}
