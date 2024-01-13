package br.com.bagnascojhoel.kwik_ecommerce.product.application;


import br.com.bagnascojhoel.kwik_ecommerce.AbstractApplicationServiceTest;
import br.com.bagnascojhoel.kwik_ecommerce.product.domain.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ContextConfiguration(classes = ProductApplicationService.class)
class ProductApplicationServiceTest extends AbstractApplicationServiceTest {

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private ProductFactory productFactory;

    @Autowired
    private ProductApplicationService productApplicationService;

    @Test
    void serviceShouldBeAnnotatedWithValidated() {
        assertThat(ProductApplicationService.class.isAnnotationPresent(Validated.class)).isTrue();
    }

    @Nested
    class SaveProduct {
        @Test
        void nonNullProduct() {
            assertThatThrownBy(() -> productApplicationService.saveProduct(null))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        void commandIsValid() throws NoSuchFieldException, NoSuchMethodException {
            assertMethodHasValidParam(ProductApplicationService.class, "saveProduct", "command");
        }

        @Test
        void passOnTransactionToRepository() {
            setupTransactionName("create-product");

            when(productFactory.createFromSaveCommand(any())).thenReturn(ProductDomainFixtures.PEPERONI_PIZZA);

            Mockito.
                    doAnswer(inv -> {
                        assertTransactionIsPropagated();
                        return ProductId.generate();
                    })
                    .when(productRepository)
                    .save(any());

            productApplicationService.saveProduct(ProductDomainFixtures.SAVE_PEPERONI_PIZZA);

            verify(productRepository).save(any());
        }
    }

    @Nested
    class GetProductById {
        @Test
        void nonNullProductId() {
            assertThatThrownBy(() -> productApplicationService.getProductById(null))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        void passOnTransactionToRepository() {
            Product product = ProductDomainFixtures.PEPERONI_PIZZA;
            setupTransactionName("get-product-by-id");

            Mockito.
                    doAnswer(inv -> {
                        assertTransactionIsPropagated();
                        return Optional.of(product);
                    })
                    .when(productRepository)
                    .findById(product.getId());

            productApplicationService.getProductById(product.getId());
        }

        @Test
        void throwsProductNotFound() {
            Product product = ProductDomainFixtures.PEPERONI_PIZZA;

            when(productRepository.findById(product.getId())).thenReturn(Optional.empty());

            assertThatThrownBy(() -> productApplicationService.getProductById(product.getId()))
                    .hasFieldOrPropertyWithValue("errorCode", "product-not-found")
                    .isInstanceOf(ProductNotFoundException.class);
        }
    }

    @Nested
    class ShowProduct {
        @Test
        void nonNullProductId() {
            assertThatThrownBy(() -> productApplicationService.showProduct(null))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        void passOnTransactionToRepository() {
            Product product = ProductDomainFixtures.PEPERONI_PIZZA;
            setupTransactionName("show-product");

            Mockito.
                    doAnswer(inv -> {
                        assertTransactionIsPropagated();
                        return Optional.of(product);
                    })
                    .when(productRepository)
                    .findById(product.getId());

            productApplicationService.showProduct(product.getId());
        }

        @Test
        void throwsProductNotFound() {
            Product product = ProductDomainFixtures.PEPERONI_PIZZA;

            when(productRepository.findById(product.getId())).thenReturn(Optional.empty());

            assertThatThrownBy(() -> productApplicationService.showProduct(product.getId()))
                    .hasFieldOrPropertyWithValue("errorCode", "product-not-found")
                    .isInstanceOf(ProductNotFoundException.class);
        }
    }

    @Nested
    class HideProduct {
        @Test
        void nonNullProductId() {
            assertThatThrownBy(() -> productApplicationService.hideProduct(null))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        void passOnTransactionToRepository() {
            Product product = ProductDomainFixtures.PEPERONI_PIZZA;
            setupTransactionName("hide-product");

            Mockito.
                    doAnswer(inv -> {
                        assertTransactionIsPropagated();
                        return Optional.of(product);
                    })
                    .when(productRepository)
                    .findById(product.getId());

            productApplicationService.hideProduct(product.getId());
        }

        @Test
        void throwsProductNotFound() {
            Product product = ProductDomainFixtures.PEPERONI_PIZZA;

            when(productRepository.findById(product.getId())).thenReturn(Optional.empty());

            assertThatThrownBy(() -> productApplicationService.showProduct(product.getId()))
                    .hasFieldOrPropertyWithValue("errorCode", "product-not-found")
                    .isInstanceOf(ProductNotFoundException.class);
        }

    }

    @Nested
    class ArchiveProduct {
        @Test
        void nonNullProductId() {
            assertThatThrownBy(() -> productApplicationService.archiveProduct(null))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        void passOnTransactionToRepository() {
            Product product = ProductDomainFixtures.PEPERONI_PIZZA;
            setupTransactionName("archive-product");

            Mockito.
                    doAnswer(inv -> {
                        assertTransactionIsPropagated();
                        return Optional.of(product);
                    })
                    .when(productRepository)
                    .findById(product.getId());

            productApplicationService.archiveProduct(product.getId());
        }

        @Test
        void throwsProductNotFound() {
            Product product = ProductDomainFixtures.PEPERONI_PIZZA;

            when(productRepository.findById(product.getId())).thenReturn(Optional.empty());

            assertThatThrownBy(() -> productApplicationService.showProduct(product.getId()))
                    .hasFieldOrPropertyWithValue("errorCode", "product-not-found")
                    .isInstanceOf(ProductNotFoundException.class);
        }
    }

    @Nested
    class FindAllProducts {
        @Test
        void passOnTransactionToRepository() {
            setupTransactionName("find-all-products");

            Mockito.
                    doAnswer(inv -> {
                        assertTransactionIsPropagated();
                        return List.of(ProductDomainFixtures.PEPERONI_PIZZA);
                    })
                    .when(productRepository)
                    .findAll();

            productApplicationService.findAllProducts();
        }
    }
}