package br.com.bagnascojhoel.kwik_ecommerce.product.driving_infra.rest;

import br.com.bagnascojhoel.kwik_ecommerce.product.application.ProductApplicationService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import java.util.List;

import static br.com.bagnascojhoel.kwik_ecommerce.product.domain.ProductDomainFixtures.DOUBLE_CHEESE_BURGUER;
import static br.com.bagnascojhoel.kwik_ecommerce.product.domain.ProductDomainFixtures.PEPERONI_PIZZA;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@Import({CustomerProductRestController.class, ProductDtoFactory.class})
public class CustomerProductRestControllerTest extends AbstractRestControllerTest {

    @MockBean
    private ProductApplicationService productApplicationService;

    @Nested
    class GetProducts {
        @Test
        @DisplayName("GET /customer/products - 200 OK")
        void shouldBeAllCustomerProducts() {
            Mockito.when(productApplicationService.findAllProductsToShowCustomers())
                    .thenReturn(List.of(PEPERONI_PIZZA, DOUBLE_CHEESE_BURGUER));

            RestAssuredMockMvc
                    .given()
                    .get("/customer/products")
                    .then()
                    .statusCode(200)
                    .body(matchesJsonSchemaInClasspath(ProductRestFixtures.ALL_PRODUCTS_JSON_PATH));

            Mockito.verify(productApplicationService).findAllProductsToShowCustomers();
        }
    }

}
