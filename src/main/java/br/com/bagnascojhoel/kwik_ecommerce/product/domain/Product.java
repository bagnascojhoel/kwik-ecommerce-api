package br.com.bagnascojhoel.kwik_ecommerce.product.domain;

import br.com.bagnascojhoel.kwik_ecommerce.common.domain.Validatable;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

import static br.com.bagnascojhoel.kwik_ecommerce.product.domain.ProductState.*;
import static java.util.Optional.ofNullable;

@Getter
@Setter
@EqualsAndHashCode
@Builder(toBuilder = true)
public final class Product implements Validatable {

    @NonNull
    private final ProductId id;

    @NonNull
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

    public boolean canBeShownToCustomer() {
        return SHOWN.equals(this.state);
    }

    public boolean isArchived() {
        return ARCHIVED.equals(this.state);
    }

    public Product archive() {
        return toBuilder().state(ARCHIVED).build();
    }

    public Product hide() {
        return toBuilder().state(HIDDEN).build();
    }

    public Product show() {
        return toBuilder().state(SHOWN).build();
    }

    public boolean isOnState(@NonNull final ProductState productState) {
        return this.state.equals(productState);
    }

    public Product update(@NonNull final SaveProductCommand saveProductCommand) {
        var builder = this.toBuilder();

        ofNullable(saveProductCommand.getImageUrl()).map(builder::imageUrl);
        ofNullable(saveProductCommand.getName()).map(builder::name);
        ofNullable(saveProductCommand.getDescription()).map(builder::description);
        ofNullable(saveProductCommand.getPriceInBrl()).map(builder::priceInBrl);

        return builder.build();
    }
}
