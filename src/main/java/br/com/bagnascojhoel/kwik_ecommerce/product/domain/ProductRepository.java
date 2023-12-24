package br.com.bagnascojhoel.kwik_ecommerce.product.domain;

import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    void save(@NonNull final Product product);

    Optional<Product> findById(@NonNull final ProductId productId);

    List<Product> findAllByState(@NonNull final ProductState productState);

    List<Product> findAll();
}
