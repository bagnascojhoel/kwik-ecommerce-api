package br.com.bagnascojhoel.kwik_ecommerce.product.domain;

import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProductFactory {
    public Product createFromSaveCommand(@NonNull final SaveProductCommand command) {
        final Product.ProductBuilder builder = Product.builder()
                .name(command.getName())
                .description(command.getDescription())
                .priceInBrl(command.getPriceInBrl())
                .imageUrl(command.getImageUrl());

        Optional.ofNullable(command.getId())
                .ifPresent(builder::id);

        return builder.build();
    }
}
