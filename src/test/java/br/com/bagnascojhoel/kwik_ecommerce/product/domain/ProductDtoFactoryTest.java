package br.com.bagnascojhoel.kwik_ecommerce.product.domain;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ProductDtoFactoryTest {

    final ProductFactory productFactory = new ProductFactory();

    @Nested
    class CreateFromSaveCommand {
        @Test
        void shouldCorrectlyFillProduct() {
            var command = SaveProductCommand.builder()
                    .id(ProductId.generate())
                    .name("name")
                    .description("description")
                    .priceInBrl(new BigDecimal("10.0"))
                    .imageUrl("imageUrl")
                    .build();

            var product = productFactory.createFromSaveCommand(command);

            assertThat(product.getId()).isEqualTo(command.getId());
            assertThat(product.getName()).isEqualTo(command.getName());
            assertThat(product.getDescription()).isEqualTo(command.getDescription());
            assertThat(product.getPriceInBrl()).isEqualTo(command.getPriceInBrl());
            assertThat(product.getImageUrl()).isEqualTo(command.getImageUrl());
        }
    }
}