package br.com.bagnascojhoel.kwik_ecommerce.product.application;


import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.bagnascojhoel.kwik_ecommerce.AbstractApplicationServiceTest;
import br.com.bagnascojhoel.kwik_ecommerce.product.domain.Product;
import br.com.bagnascojhoel.kwik_ecommerce.product.domain.ProductDomainFixtures;
import br.com.bagnascojhoel.kwik_ecommerce.product.domain.ProductId;
import br.com.bagnascojhoel.kwik_ecommerce.product.domain.ProductNotFoundException;
import br.com.bagnascojhoel.kwik_ecommerce.product.domain.ProductRepository;
import jakarta.validation.Validator;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;


@ContextConfiguration(classes = ProductApplicationService.class)
class ProductApplicationServiceTest extends AbstractApplicationServiceTest {

  @MockBean
  private ProductRepository productRepository;

  @MockBean
  private Validator validator;

  @Autowired
  private ProductApplicationService productApplicationService;

  @Nested
  class SaveProduct {

    @Test
    void nonNullProduct() {
      assertThatThrownBy(() -> productApplicationService.saveProduct(null, null))
          .isInstanceOf(NullPointerException.class);
    }

    @Test
    void commandIsValid() throws NoSuchFieldException, NoSuchMethodException {
      assertMethodHasValidParam(ProductApplicationService.class, "saveProduct", "command");
    }

    @Test
    void passOnTransactionToRepository() {
      setupTransactionName("save-product");

      Mockito.
          doAnswer(inv -> {
            assertTransactionIsPropagated();
            return ProductId.generate();
          })
          .when(productRepository)
          .save(any());

      productApplicationService.saveProduct(null, ProductDomainFixtures.SAVE_PEPERONI_PIZZA);

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