package br.com.bagnascojhoel.kwik_ecommerce.product.driving_infra.rest;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.bagnascojhoel.kwik_ecommerce.product.application.ProductApplicationService;
import br.com.bagnascojhoel.kwik_ecommerce.product.domain.ProductDomainFixtures;
import br.com.bagnascojhoel.kwik_ecommerce.product.domain.ProductId;
import br.com.bagnascojhoel.kwik_ecommerce.product.domain.ProductState;
import br.com.bagnascojhoel.kwik_ecommerce.product.domain.SaveProductCommand;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;


@Import({ProductDtoFactory.class, ManagementProductRestController.class})
public class ManagementProductRestControllerTest extends AbstractRestControllerTest {

  @MockBean
  private ProductApplicationService productApplicationService;

  @Nested
  class PostProducts {

    @Test
    @DisplayName("POST /management/products - 201 CREATED")
    void shouldPostToProductSuccessfully() {
      when(productApplicationService.saveProduct(eq(null), any())).thenReturn(
          ProductDomainFixtures.PEPERONI_PIZZA);

      RestAssuredMockMvc
          .given()
          .body(loadJson(ProductRestFixtures.PEPERONI_PIZZA_JSON_PATH))
          .contentType(ContentType.JSON)
          .post("/management/products")
          .then()
          .statusCode(201)
          .body(matchesJsonSchemaInClasspath(ProductRestFixtures.PEPERONI_PIZZA_JSON_PATH));

      verify(productApplicationService).saveProduct(eq(null), any());
    }

  }

  @Nested
  class GetAllProducts {

    @Test
    @DisplayName("GET /management/products - 200 OK")
    void shouldGetProductsSuccessfully() {
      // it is not relevant that I am using a hidden product since I am not testing business logic
      when(productApplicationService.findAllProducts())
          .thenReturn(List.of(ProductDomainFixtures.PEPERONI_PIZZA,
              ProductDomainFixtures.DOUBLE_CHEESE_BURGUER));

      RestAssuredMockMvc
          .given()
          .get("/management/products")
          .then()
          .statusCode(200)
          .body(matchesJsonSchemaInClasspath(ProductRestFixtures.ALL_PRODUCTS_JSON_PATH));

      verify(productApplicationService).findAllProducts();
    }
  }

  @Nested
  class GetProductById {

    @Test
    @DisplayName("GET /management/products/{productId} - 200 OK")
    void shouldGetProductSuccessfully() {
      var productId = ProductId.generate();

      when(productApplicationService.getProductById(productId))
          .thenReturn(ProductDomainFixtures.PEPERONI_PIZZA);

      RestAssuredMockMvc
          .given()
          .get("/management/products/{productId}", productId.toString())
          .then()
          .statusCode(200)
          .body(matchesJsonSchemaInClasspath(ProductRestFixtures.PEPERONI_PIZZA_JSON_PATH));

      verify(productApplicationService).getProductById(productId);
    }
  }

  @Nested
  class PatchProduct {

    @Test
    @DisplayName("PATCH /management/products/{productId} - 200 OK")
    void shouldPatchProduct() {
      var productId = ProductId.generate();
      var command = SaveProductCommand.builder()
          .productState(ProductState.SHOWN)
          .build();

      when(productApplicationService.saveProduct(productId, command))
          .thenReturn(ProductDomainFixtures.PEPERONI_PIZZA);

      RestAssuredMockMvc
          .given()
          .body(loadJson(ProductRestFixtures.PRODUCT_STATE_SHOWN_JSON_PATH))
          .contentType(ContentType.JSON)
          .patch("/management/products/{productId}", productId.toString())
          .then()
          .statusCode(200)
          .body(matchesJsonSchemaInClasspath(ProductRestFixtures.PEPERONI_PIZZA_JSON_PATH));

      verify(productApplicationService).saveProduct(productId, command);
    }
  }

}
