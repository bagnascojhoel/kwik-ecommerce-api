package br.com.bagnascojhoel.kwik_ecommerce.product.infrastructure.driving_ports.rest;

import br.com.bagnascojhoel.kwik_ecommerce.common.infrastructure.driving_ports.rest.resource_representations.EnumeratedValueRepresentation;
import br.com.bagnascojhoel.kwik_ecommerce.product.domain.Product;
import br.com.bagnascojhoel.kwik_ecommerce.product.domain.ProductId;
import br.com.bagnascojhoel.kwik_ecommerce.product.domain.SaveProductCommand;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ProductResourceFactory {
    public SaveProductCommand createSaveCommand(@NonNull final ProductRepresentation resource) {
        var builder = SaveProductCommand.builder()
                .name(resource.getProductName())
                .description(resource.getProductDescription())
                .imageUrl(resource.getImageUrl())
                .priceInBrl(resource.getPriceInBrl());

        Optional.ofNullable(resource.getProductId())
                .map(UUID::fromString)
                .map(ProductId::new)
                .ifPresent(builder::id);

        return builder.build();
    }

    public ProductCollectionRepresentation createCollection(@NonNull final List<Product> products) {
        return new ProductCollectionRepresentation(
                products.stream()
                        .map(this::create)
                        .collect(Collectors.toList())
        );
    }

    public ProductRepresentation create(@NonNull final Product product) {
        return ProductRepresentation.builder()
                .productId(product.getId().toString())
                .productState(new EnumeratedValueRepresentation(product.getState().getCode(), product.getState().name()))
                .productName(product.getName())
                .productDescription(product.getDescription())
                .imageUrl(product.getImageUrl())
                .priceInBrl(product.getPriceInBrl())
                .build();
    }

}
