package br.com.bagnascojhoel.kwik_ecommerce.product;

import static br.com.bagnascojhoel.kwik_ecommerce.common.infrastructure.CommonTestConstants.TEST_CONTAINER_POSTGRES_JDBC_URL;
import static br.com.bagnascojhoel.kwik_ecommerce.product.domain.ProductDomainFixtures.PEPERONI_PIZZA;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.mockMvc;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;

import br.com.bagnascojhoel.kwik_ecommerce.KwikEcommerceApplication;
import br.com.bagnascojhoel.kwik_ecommerce.product.domain.ProductRepository;
import br.com.bagnascojhoel.kwik_ecommerce.product.driving_infra.rest.ProductRestFixtures;
import io.restassured.http.ContentType;
import java.nio.charset.StandardCharsets;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(
    // TODO Change this to spin-up only the module, not the whole application
    classes = KwikEcommerceApplication.class,
    properties = {
        "spring.datasource.hikari.jdbc-url=" + TEST_CONTAINER_POSTGRES_JDBC_URL
            + "?currentSchema=product",
        "spring.datasource.driver-class-name=org.testcontainers.jdbc.ContainerDatabaseDriver"
    }
)
@AutoConfigureMockMvc
public class ProductModuleHappyPathIntegrationTest {

  @Autowired
  private ResourceLoader resourceLoader;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  @Qualifier("jdbcProductRepository")
  private ProductRepository productRepository;

  @BeforeEach
  void beforeEach() {
    mockMvc(mockMvc);
  }

  @AfterEach
  void afterEach() {
    JdbcTestUtils.deleteFromTables(jdbcTemplate, "product");
  }

  @SneakyThrows
  private String loadJson(@NonNull final String filePath) {
    return new String(
        resourceLoader.getResource("classpath:" + filePath).getInputStream().readAllBytes(),
        StandardCharsets.UTF_8);
  }

  @Test
  @DisplayName("POST /management/products")
  void postManagerProducts() {
    given()
        .body(loadJson(ProductRestFixtures.PEPERONI_PIZZA_JSON_PATH))
        .contentType(ContentType.JSON)
        .post("/management/products")
        .then()
        .statusCode(201);
  }

  @Test
  @DisplayName("GET /management/products")
  void getManagerProducts() {
    productRepository.save(PEPERONI_PIZZA);

    given()
        .get("/management/products")
        .then()
        .statusCode(200)
        .body(not(isEmptyOrNullString()));
  }

  @Test
  @DisplayName("GET /customer/products")
  void getCustomerProducts() {
    productRepository.save(PEPERONI_PIZZA);

    given()
        .get("/customer/products")
        .then()
        .statusCode(200)
        .body(not(isEmptyOrNullString()));
  }

  @Test
  @DisplayName("GET /management/products/{productId}")
  void getProductById() {
    productRepository.save(PEPERONI_PIZZA);

    given()
        .get("/management/products/{productId}", PEPERONI_PIZZA.getId().toString())
        .then()
        .statusCode(200);
  }

  @Test
  @DisplayName("PATCH /management/products/{productId}")
  void putProductState() {
    productRepository.save(PEPERONI_PIZZA);

    given()
        .body(loadJson(ProductRestFixtures.PRODUCT_NAME_IMAGE_JSON_PATH))
        .contentType(ContentType.JSON)
        .patch("/management/products/{productId}", PEPERONI_PIZZA.getId().toString())
        .then()
        .statusCode(200);
  }

}
