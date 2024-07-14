package br.com.bagnascojhoel.kwik_ecommerce.product.application;

import br.com.bagnascojhoel.kwik_ecommerce.product.domain.Product;
import br.com.bagnascojhoel.kwik_ecommerce.product.domain.ProductId;
import br.com.bagnascojhoel.kwik_ecommerce.product.domain.ProductNotFoundException;
import br.com.bagnascojhoel.kwik_ecommerce.product.domain.ProductRepository;
import br.com.bagnascojhoel.kwik_ecommerce.product.domain.ProductState;
import br.com.bagnascojhoel.kwik_ecommerce.product.domain.SaveProductCommand;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ProductApplicationService {

  private final ProductRepository productRepository;
  private final Validator validator;

  @Transactional
  public Product saveProduct(
      @Nullable final ProductId productId,
      @NonNull @Valid final SaveProductCommand command
  ) {
    final Product product = Optional.ofNullable(productId)
        .flatMap(productRepository::findById)
        .map(p -> p.update(command))
        .orElseGet(() -> Product.create(command));
    validator.validate(product);
    this.productRepository.save(product);
    return product;
  }

  @Transactional
  public Product getProductById(@NonNull final ProductId productId) {
    return this.productRepository.findById(productId)
        .orElseThrow(ProductNotFoundException::new);
  }

  @Transactional
  public List<Product> findAllProducts() {
    return this.productRepository.findAll();
  }

  @Transactional
  public List<Product> findAllProductsToShowCustomers() {
    return this.productRepository.findAllByState(ProductState.SHOWN);
  }

}
