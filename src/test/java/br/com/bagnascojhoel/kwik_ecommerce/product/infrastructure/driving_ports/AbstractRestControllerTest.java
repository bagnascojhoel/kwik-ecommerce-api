package br.com.bagnascojhoel.kwik_ecommerce.product.infrastructure.driving_ports;

import br.com.bagnascojhoel.kwik_ecommerce.common.infrastructure.ObjectMapperConfiguration;
import br.com.bagnascojhoel.kwik_ecommerce.common.infrastructure.driving_ports.rest.resource_representations.FailedValidationRepresentationFactory;
import br.com.bagnascojhoel.kwik_ecommerce.product.infrastructure.driving_ports.rest.RestExceptionHandler;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

@WebMvcTest({RestExceptionHandler.class, ObjectMapperConfiguration.class, FailedValidationRepresentationFactory.class})
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
        return new String(resourceLoader.getResource("classpath:" + filePath).getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }

}
