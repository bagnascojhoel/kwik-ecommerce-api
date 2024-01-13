package br.com.bagnascojhoel.kwik_ecommerce.product.driving_infra.rest;

import br.com.bagnascojhoel.kwik_ecommerce.product.application.ProductApplicationService;
import br.com.bagnascojhoel.kwik_ecommerce.product.domain.Product;
import br.com.bagnascojhoel.kwik_ecommerce.product.domain.ProductDomainFixtures;
import br.com.bagnascojhoel.kwik_ecommerce.product.domain.ProductId;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@Import({ProductDtoFactory.class, ProductRestController.class})
public class ProductRestControllerTest extends AbstractRestControllerTest {

    @MockBean
    private ProductApplicationService productApplicationService;

    @Nested
    class PostProducts {

        @Test
        @DisplayName("POST /products - 201 CREATED")
        void shouldPostToProductSuccessfully() {
            when(productApplicationService.saveProduct(any())).thenReturn(ProductId.generate());

            RestAssuredMockMvc
                    .given()
                    .body(loadJson(ProductRestFixtures.PEPERONI_PIZZA_JSON_PATH))
                    .contentType(ContentType.JSON)
                    .post("/products")
                    .then()
                    .statusCode(201)
                    .body(matchesJsonSchemaInClasspath(ProductRestFixtures.PEPERONI_PIZZA_JSON_PATH));


            verify(productApplicationService).saveProduct(any());
        }

    }

    @Nested
    class GetAllProducts {
        @Test
        @DisplayName("GET /products - 200 OK")
        void shouldGetProductsSuccessfully() {
            // it is not relevant that I am using a hidden product since I am not testing business logic
            when(productApplicationService.findAllProducts())
                    .thenReturn(List.of(ProductDomainFixtures.PEPERONI_PIZZA, ProductDomainFixtures.DOUBLE_CHEESE_BURGUER));

            RestAssuredMockMvc
                    .given()
                    .get("/products")
                    .then()
                    .statusCode(200)
                    .body(matchesJsonSchemaInClasspath(ProductRestFixtures.ALL_PRODUCTS_JSON_PATH));

            verify(productApplicationService).findAllProducts();
        }
    }

    @Nested
    class GetProductById {
        @Test
        @DisplayName("GET /products/{productId} - 200 OK")
        void shouldGetProductSuccessfully() {
            var productId = ProductId.generate();

            when(productApplicationService.getProductById(productId))
                    .thenReturn(ProductDomainFixtures.PEPERONI_PIZZA);

            RestAssuredMockMvc
                    .given()
                    .get("/products/{productId}", productId.toString())
                    .then()
                    .statusCode(200)
                    .body(matchesJsonSchemaInClasspath(ProductRestFixtures.PEPERONI_PIZZA_JSON_PATH));

            verify(productApplicationService).getProductById(productId);
        }
    }

    @Nested
    class PutProductState {
        @Test
        @DisplayName("PUT /products/{productId}/state - 200 OK")
        void shouldPutProductState() {
            var productId = ProductId.generate();

            RestAssuredMockMvc
                    .given()
                    .body(loadJson(ProductRestFixtures.PRODUCT_STATE_SHOWN_JSON_PATH))
                    .contentType(ContentType.JSON)
                    .put("/products/{productId}/state", productId.toString())
                    .then()
                    .statusCode(200);

            verify(productApplicationService).showProduct(productId);
        }
    }

}
