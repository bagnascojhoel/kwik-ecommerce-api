package br.com.bagnascojhoel.kwik_ecommerce.product.infrastructure.driven_ports.product;

import br.com.bagnascojhoel.kwik_ecommerce.product.domain.Product;
import br.com.bagnascojhoel.kwik_ecommerce.product.domain.ProductId;
import br.com.bagnascojhoel.kwik_ecommerce.product.domain.ProductRepository;
import br.com.bagnascojhoel.kwik_ecommerce.product.domain.ProductState;
import lombok.NonNull;
import org.springframework.boot.test.context.TestComponent;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@TestComponent
public class InMemoryProductRepository implements ProductRepository {
    private final List<Product> products = new LinkedList<>();

    @Override
    public void save(@NonNull Product product) {
        this.products.stream()
                .filter(p -> p.getId().equals(product.getId()))
                .findAny()
                .ifPresentOrElse(
                        p -> {
                            products.remove(p);
                            products.add(product);
                        },
                        () -> products.add(product)
                );
    }

    @Override
    public Optional<Product> findById(@NonNull ProductId productId) {
        return this.products.stream()
                .filter(p -> p.getId().equals(productId))
                .findAny();
    }

    @Override
    public List<Product> findAllByState(@NonNull ProductState productState) {
        return this.products.stream()
                .filter(product -> product.isOnState(productState))
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> findAll() {
        return this.products;
    }
}
