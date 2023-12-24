package br.com.bagnascojhoel.kwik_ecommerce.product.infrastructure.driving_ports;

import br.com.bagnascojhoel.kwik_ecommerce.product.application.ProductApplicationService;
import br.com.bagnascojhoel.kwik_ecommerce.product.infrastructure.driving_ports.rest.CustomerProductRestController;
import br.com.bagnascojhoel.kwik_ecommerce.product.infrastructure.driving_ports.rest.ProductResourceFactory;
import br.com.bagnascojhoel.kwik_ecommerce.product.infrastructure.driving_ports.rest.RestBasePaths;
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

@Import({CustomerProductRestController.class, ProductResourceFactory.class})
public class CustomerProductRestControllerTest extends AbstractRestControllerTest {

    @MockBean
    private ProductApplicationService productApplicationService;

    @Nested
    class GetProducts {
        @Test
        @DisplayName("GET " + RestBasePaths.CUSTOMER_PRODUCTS + " - 200 OK")
        void shouldBeAllCustomerProducts() {
            Mockito.when(productApplicationService.findAllProductsToShowCustomers())
                    .thenReturn(List.of(PEPERONI_PIZZA, DOUBLE_CHEESE_BURGUER));

            RestAssuredMockMvc
                    .given()
                    .get(RestBasePaths.CUSTOMER_PRODUCTS)
                    .then()
                    .statusCode(200)
                    .body(matchesJsonSchemaInClasspath(ProductRestFixtures.ALL_PRODUCTS_JSON_PATH));

            Mockito.verify(productApplicationService).findAllProductsToShowCustomers();
        }
    }

}
