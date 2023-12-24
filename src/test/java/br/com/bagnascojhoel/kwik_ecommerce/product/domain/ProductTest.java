package br.com.bagnascojhoel.kwik_ecommerce.product.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductTest {

    @Nested
    class Builder {
        @Test
        void shouldForbidBlankOrNullName() {
            assertThatThrownBy(() -> Product.builder()
                    .name("")
                    .priceInBrl(BigDecimal.TEN)
                    .build())
                    .isInstanceOf(ConstraintViolationException.class)
                    .satisfies((ex) -> {
                        // Access and assert on constraint violations
                        Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) ex).getConstraintViolations();
                        assertThat(constraintViolations).hasSize(1); // Adjust the size based on your expectations

                        ConstraintViolation<?> violation = constraintViolations.iterator().next();
                        assertThat(violation.getPropertyPath().toString()).isEqualTo("name");
                        assertThat(violation.getMessage()).isEqualTo("product-name-not-blank");
                    });
        }

        @Test
        void shouldForbidDescriptionGreaterThan200Characters() {
            String longDescription = "A".repeat(201); // Create a description longer than 200 characters
            assertThatThrownBy(() -> Product.builder()
                    .name("Valid Name")
                    .description(longDescription)
                    .priceInBrl(BigDecimal.TEN)
                    .build())
                    .isInstanceOf(ConstraintViolationException.class)
                    .satisfies(ex -> {
                        Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) ex).getConstraintViolations();
                        assertThat(constraintViolations).hasSize(1);

                        ConstraintViolation<?> violation = constraintViolations.iterator().next();
                        assertThat(violation.getPropertyPath().toString()).isEqualTo("description");
                        assertThat(violation.getMessage()).isEqualTo("product-description-size");
                    });
        }

        @Test
        void shouldForbidNullPrice() {
            assertThatThrownBy(() -> Product.builder()
                    .name("Valid Name")
                    .priceInBrl(null)
                    .build())
                    .isInstanceOf(ConstraintViolationException.class)
                    .satisfies(ex -> {
                        Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) ex).getConstraintViolations();
                        assertThat(constraintViolations).hasSize(1);

                        ConstraintViolation<?> violation = constraintViolations.iterator().next();
                        assertThat(violation.getPropertyPath().toString()).isEqualTo("priceInBrl");
                        assertThat(violation.getMessage()).isEqualTo("product-price-not-null");
                    });
        }

        @Test
        void shouldForbidZeroOrNegativePrice() {
            assertThatThrownBy(() -> Product.builder()
                    .name("Valid Name")
                    .priceInBrl(BigDecimal.ZERO)
                    .build())
                    .isInstanceOf(ConstraintViolationException.class)
                    .satisfies(ex -> {
                        Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) ex).getConstraintViolations();
                        assertThat(constraintViolations).hasSize(1);

                        ConstraintViolation<?> violation = constraintViolations.iterator().next();
                        assertThat(violation.getPropertyPath().toString()).isEqualTo("priceInBrl");
                        assertThat(violation.getMessage()).isEqualTo("product-price-positive");
                    });

            assertThatThrownBy(() -> Product.builder()
                    .name("Valid Name")
                    .priceInBrl(BigDecimal.valueOf(-5))
                    .build())
                    .isInstanceOf(ConstraintViolationException.class)
                    .satisfies(ex -> {
                        Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) ex).getConstraintViolations();
                        assertThat(constraintViolations).hasSize(1);

                        ConstraintViolation<?> violation = constraintViolations.iterator().next();
                        assertThat(violation.getPropertyPath().toString()).isEqualTo("priceInBrl");
                        assertThat(violation.getMessage()).isEqualTo("product-price-positive");
                    });
        }


        @Test
        void shouldUseHiddenStateWhenNotSet() {
            Assertions.assertThat(Product.builder()
                            .name("Pizza Peperoni")
                            .description("The best pizza you'll ever eat!")
                            .imageUrl("https://pizza-url.com")
                            .priceInBrl(new BigDecimal("49.99"))
                            .build())
                    .hasFieldOrPropertyWithValue("state", ProductState.HIDDEN);
        }

        @Test
        void shouldGenerateNewIdWhenNotSet() {
            Assertions.assertThat(Product.builder()
                            .name("Pizza Peperoni")
                            .description("The best pizza you'll ever eat!")
                            .imageUrl("https://pizza-url.com")
                            .priceInBrl(new BigDecimal("49.99"))
                            .build().getId())
                    .isNotNull();

        }
    }

    @Nested
    class Update {

        // If it uses toBuilder(), it will also use build() and the constructor.
        @Test
        void shouldUseToBuilder() {
            final var command = SaveProductCommand.builder()
                    .name("")
                    .build();
            final var product = Mockito.spy(ProductDomainFixtures.PEPERONI_PIZZA);

            assertThatThrownBy(() -> product.update(command))
                    .isInstanceOf(ConstraintViolationException.class);

            Mockito.verify(product).toBuilder();
        }

        @Test
        void shouldChangeAllUpdatableFields() {
            final var command = SaveProductCommand.builder()
                    .name("new name")
                    .description("new description")
                    .imageUrl("new image url")
                    .priceInBrl(BigDecimal.TEN)
                    .build();

            Product product = ProductDomainFixtures.PEPERONI_PIZZA.update(command);

            assertThat(product.getName()).isEqualTo("new name");
            assertThat(product.getDescription()).isEqualTo("new description");
            assertThat(product.getImageUrl()).isEqualTo("new image url");
            assertThat(product.getPriceInBrl()).isEqualTo(BigDecimal.TEN);
        }
    }
}