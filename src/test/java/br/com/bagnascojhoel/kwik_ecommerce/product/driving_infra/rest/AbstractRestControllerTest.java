package br.com.bagnascojhoel.kwik_ecommerce.product.driving_infra.rest;

import br.com.bagnascojhoel.kwik_ecommerce.common.common_infra.ObjectMapperConfiguration;
import br.com.bagnascojhoel.kwik_ecommerce.common.common_infra.logging.KwikLoggingProperties;
import br.com.bagnascojhoel.kwik_ecommerce.common.common_infra.logging.RequestLoggingFilterResolver;
import br.com.bagnascojhoel.kwik_ecommerce.common.driving_infra.rest.FailedValidationDtoFactory;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.nio.charset.StandardCharsets;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest({
    RestExceptionHandler.class,
    ObjectMapperConfiguration.class,
    FailedValidationDtoFactory.class,
    KwikLoggingProperties.class,
    RequestLoggingFilterResolver.class
})
public abstract class AbstractRestControllerTest {

  @Autowired
  private ResourceLoader resourceLoader;

  @Autowired
  private MockMvc mockMvc;

  @BeforeEach
  void setupRestAssured() {
    RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();
    RestAssuredMockMvc.mockMvc(mockMvc);
  }

  @SneakyThrows
  String loadJson(@NonNull final String filePath) {
    return new String(
        resourceLoader.getResource("classpath:" + filePath).getInputStream().readAllBytes(),
        StandardCharsets.UTF_8);
  }

}
