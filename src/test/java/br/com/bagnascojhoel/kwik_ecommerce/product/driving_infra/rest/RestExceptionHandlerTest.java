package br.com.bagnascojhoel.kwik_ecommerce.product.driving_infra.rest;

import br.com.bagnascojhoel.kwik_ecommerce.common.domain.AbstractResourceNotFoundException;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Supplier;

import static org.hamcrest.Matchers.*;

@Import({
        RestExceptionHandlerTest.FakeController.class,
        RestExceptionHandlerTest.FakeApplicationService.class
})
public class RestExceptionHandlerTest extends AbstractRestControllerTest {

    private static final String THROW_EXCEPTION_ENDPOINT = "/fake/throw-exception";
    private static final String THROW_METHOD_ARGUMENT_NOT_VALID_ENDPOINT = "/fake/throw-method-argument-not-valid-exception";
    private static final String THROW_CONSTRAINT_VIOLATION_ENDPOINT = "/fake/throw-constraint-violation-exception";

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void shouldHandleResourceNotFound() {
        FakeController.exceptionSupplier = () -> new AbstractResourceNotFoundException("My error title", "fake-resource-not-found") {
        };

        RestAssuredMockMvc
                .given()
                .post(THROW_EXCEPTION_ENDPOINT)
                .then()
                .statusCode(404)
                .body("type", is("/errors/client-errors/not-founds/fake-resource-not-found"))
                .body("title", is("My error title"))
                .body("failedValidations", nullValue());
    }

    @Test
    void shouldHandleMethodArgumentNotValid() {
        RestAssuredMockMvc
                .given()
                .body(new FakeResource("", new BigDecimal("51"), List.of("", "", "", "", "", "")))
                .contentType(ContentType.JSON)
                .post(THROW_METHOD_ARGUMENT_NOT_VALID_ENDPOINT)
                .then()
                .statusCode(400)
                .body("failedValidations", hasSize(3))
                .body("failedValidations.find { it.field == 'fake-resource-max-5-elements-field-size' }.reason",
                        is("fake-resource-max-5-elements-field-size"))
                .body("failedValidations.find { it.field == 'fake-resource-max-50-field-max' }.reason",
                        is("fake-resource-max-50-field-max"))
                .body("failedValidations.find { it.field == 'fake-resource-non-empty-field-not-empty' }.reason",
                        is("fake-resource-non-empty-field-not-empty"));
    }

    @Test
    void shouldHandleAny() {
        FakeController.exceptionSupplier = RuntimeException::new;

        RestAssuredMockMvc
                .given()
                .post(THROW_EXCEPTION_ENDPOINT)
                .then()
                .statusCode(500)
                .body("type", is("/errors/server-errors/internal-server-error"))
                .body("title", not(isEmptyOrNullString()))
                .body("failedValidations", nullValue());
    }

    @Test
    void shouldHandleConstraintViolation() {
        RestAssuredMockMvc
                .given()
                .body(new FakeResource("", new BigDecimal("51"), List.of("", "", "", "", "", "")))
                .contentType(ContentType.JSON)
                .post(THROW_CONSTRAINT_VIOLATION_ENDPOINT)
                .then()
                .statusCode(400)
                .body("failedValidations", hasSize(3))
                .body("failedValidations.find { it.field == 'fake-resource-max-5-elements-field-size' }.reason",
                        is("fake-resource-max-5-elements-field-size"))
                .body("failedValidations.find { it.field == 'fake-resource-max-50-field-max' }.reason",
                        is("fake-resource-max-50-field-max"))
                .body("failedValidations.find { it.field == 'fake-resource-non-empty-field-not-empty' }.reason",
                        is("fake-resource-non-empty-field-not-empty"));
    }

    @RestController
    @AllArgsConstructor
    @RequestMapping("/fake")
    @Validated
    public static class FakeController {
        public static Supplier<Exception> exceptionSupplier;

        private final FakeApplicationService fakeApplicationService;

        @PostMapping("/throw-exception")
        public void throwException() throws Exception {
            throw exceptionSupplier.get();
        }

        @PostMapping("/throw-method-argument-not-valid-exception")
        public void throwMethodArgumentNotValidException(@RequestBody @Valid FakeResource fakeResource) {

        }

        @PostMapping("/throw-constraint-violation-exception")
        public void throwConstraintViolationException() {
            fakeApplicationService.test(new FakeResource("", new BigDecimal(55), List.of("", "", "", "", "", "")));
        }
    }

    @Component
    @Validated
    public static class FakeApplicationService {
        public void test(@Valid FakeResource fakeResource) {

        }
    }

    @AllArgsConstructor
    @Data
    @NoArgsConstructor
    @Validated
    public static class FakeResource {
        @NotEmpty(message = "fake-resource-non-empty-field-not-empty")
        private String nonEmptyField;

        @Max(value = 50, message = "fake-resource-max-50-field-max")
        private BigDecimal max50Field;

        @Size(max = 5, message = "fake-resource-max-5-elements-field-size")
        private List<String> max5ElementsField;
    }

}
