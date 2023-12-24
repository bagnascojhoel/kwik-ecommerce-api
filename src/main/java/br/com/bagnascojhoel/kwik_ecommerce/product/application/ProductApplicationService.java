package br.com.bagnascojhoel.kwik_ecommerce.product.application;

import br.com.bagnascojhoel.kwik_ecommerce.product.domain.*;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Validated
public class ProductApplicationService {

    private final ProductRepository productRepository;

    private final ProductFactory productFactory;

    @Transactional
    public ProductId saveProduct(@NonNull @Valid final SaveProductCommand command) throws ConstraintViolationException {
        final Product product = Optional.ofNullable(command.getId())
                .flatMap(productRepository::findById)
                .map(p -> p.update(command))
                .orElseGet(() -> this.productFactory.createFromSaveCommand(command));
        this.productRepository.save(product);
        return product.getId();
    }

    @Transactional
    public Product getProductById(@NonNull final ProductId productId) {
        return this.productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);
    }

    @Transactional
    public void showProduct(@NonNull final ProductId productId) {
        this.productRepository.findById(productId)
                .map(Product::show)
                .ifPresentOrElse(productRepository::save, this::throwProductNotFound);
    }

    @Transactional
    public void hideProduct(@NonNull final ProductId productId) {
        this.productRepository.findById(productId)
                .map(Product::hide)
                .ifPresentOrElse(productRepository::save, this::throwProductNotFound);
    }

    @Transactional
    public void archiveProduct(@NonNull final ProductId productId) {
        this.productRepository.findById(productId)
                .map(Product::archive)
                .ifPresentOrElse(productRepository::save, this::throwProductNotFound);
    }

    @Transactional
    public List<Product> findAllProducts() {
        return this.productRepository.findAll();
    }

    @Transactional
    public List<Product> findAllProductsToShowCustomers() {
        return this.productRepository.findAllByState(ProductState.SHOWN);
    }

    private void throwProductNotFound() {
        throw new ProductNotFoundException();
    }
}
