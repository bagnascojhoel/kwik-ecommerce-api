package br.com.bagnascojhoel.kwik_ecommerce.product.driving_infra.rest;

import br.com.bagnascojhoel.kwik_ecommerce.common.driving_infra.rest.EnumeratedValueDto;
import br.com.bagnascojhoel.kwik_ecommerce.product.domain.Product;
import br.com.bagnascojhoel.kwik_ecommerce.product.domain.ProductId;
import br.com.bagnascojhoel.kwik_ecommerce.product.domain.ProductState;
import br.com.bagnascojhoel.kwik_ecommerce.product.domain.SaveProductCommand;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class ProductDtoFactory {

  public SaveProductCommand createSaveCommand(@NonNull final ProductDto resource) {
    var builder = SaveProductCommand.builder()
        .name(resource.getProductName())
        .description(resource.getProductDescription())
        .imageUrl(resource.getImageUrl())
        .priceInBrl(resource.getPriceInBrl())
        .productState(Optional.ofNullable(resource.getProductState())
            .map(EnumeratedValueDto::getCode)
            .map(ProductState::valueOf)
            .orElse(null));

    return builder.build();
  }

  public ProductCollectionDto createCollection(@NonNull final List<Product> products) {
    return new ProductCollectionDto(
        products.stream()
            .map(this::create)
            .collect(Collectors.toList())
    );
  }

  public ProductDto create(@NonNull final Product product) {
    return ProductDto.builder()
        .productId(product.getId().toString())
        .productState(new EnumeratedValueDto(product.getState().name(), product.getState().name()))
        .productName(product.getName())
        .productDescription(product.getDescription())
        .imageUrl(product.getImageUrl())
        .priceInBrl(product.getPriceInBrl())
        .build();
  }

  public ProductDto create(@NonNull final ProductId productId) {
    return ProductDto.builder()
        .productId(productId.toString())
        .build();
  }

}
