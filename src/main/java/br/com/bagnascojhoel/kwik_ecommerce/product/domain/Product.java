package br.com.bagnascojhoel.kwik_ecommerce.product.domain;

import static br.com.bagnascojhoel.kwik_ecommerce.product.domain.ProductState.ARCHIVED;
import static br.com.bagnascojhoel.kwik_ecommerce.product.domain.ProductState.HIDDEN;
import static br.com.bagnascojhoel.kwik_ecommerce.product.domain.ProductState.SHOWN;
import static java.util.Optional.ofNullable;

import br.com.bagnascojhoel.kwik_ecommerce.common.domain.Validatable;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Optional;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Builder(toBuilder = true)
public final class Product implements Validatable {

  @NotNull
  private final ProductId id;

  @NotNull
  private final ProductState state;

  @NotBlank(message = "product-name-not-blank")
  private final String name;

  @Nullable
  @Size(max = 200, message = "product-description-size")
  private final String description;

  @NotNull(message = "product-price-not-null")
  @Positive(message = "product-price-positive")
  private final BigDecimal priceInBrl;

  private final String imageUrl;

  private Product(
      ProductId id,
      ProductState state,
      String name,
      @Nullable String description,
      BigDecimal priceInBrl,
      String imageUrl
  ) {
    this.id = id == null ? ProductId.generate() : id;
    this.state = state == null ? HIDDEN : state;
    this.name = name;
    this.description = description;
    this.priceInBrl = priceInBrl;
    this.imageUrl = imageUrl;

    validate();
  }

  public static Product create(@NonNull final SaveProductCommand command) {
    final Product.ProductBuilder builder = Product.builder();

    builder.id(ProductId.generate());
    builder.state(HIDDEN);
    Optional.ofNullable(command.getName()).ifPresent(builder::name);
    Optional.ofNullable(command.getDescription()).ifPresent(builder::description);
    Optional.ofNullable(command.getImageUrl()).ifPresent(builder::imageUrl);
    Optional.ofNullable(command.getPriceInBrl()).ifPresent(builder::priceInBrl);

    return builder.build();
  }

  public boolean canBeShownToCustomer() {
    return SHOWN.equals(this.state);
  }

  public boolean isArchived() {
    return ARCHIVED.equals(this.state);
  }

  public boolean isOnState(@NonNull final ProductState productState) {
    return this.state.equals(productState);
  }

  public Product update(@NonNull final SaveProductCommand saveProductCommand) {
    var builder = this.toBuilder();

    ofNullable(saveProductCommand.getImageUrl()).ifPresent(builder::imageUrl);
    ofNullable(saveProductCommand.getName()).ifPresent(builder::name);
    ofNullable(saveProductCommand.getDescription()).ifPresent(builder::description);
    ofNullable(saveProductCommand.getPriceInBrl()).ifPresent(builder::priceInBrl);
    ofNullable(saveProductCommand.getProductState()).ifPresent(builder::state);

    return builder.build();
  }
}
