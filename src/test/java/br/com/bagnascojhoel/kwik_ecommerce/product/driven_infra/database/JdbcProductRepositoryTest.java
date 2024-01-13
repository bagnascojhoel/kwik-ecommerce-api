package br.com.bagnascojhoel.kwik_ecommerce.product.driven_infra.database;

import br.com.bagnascojhoel.kwik_ecommerce.product.domain.ProductDomainFixtures;
import br.com.bagnascojhoel.kwik_ecommerce.product.domain.ProductState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.jdbc.JdbcTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Import({JdbcProductRepository.class, ProductParameterSource.class, ProductRowMapper.class})
public class JdbcProductRepositoryTest extends AbstractJdbcRepositoryTest {

    @Autowired
    private JdbcProductRepository jdbcProductRepository;

    @BeforeEach
    void beforeEach() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "product");
    }

    @Nested
    class Save {
        @Test
        void shouldThrowWhenProductIsNull() {
            assertThatThrownBy(() -> jdbcProductRepository.save(null))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        void shouldInsertProductWhenIdDoesNotExist() {
            jdbcProductRepository.save(ProductDomainFixtures.PEPERONI_PIZZA);

            JdbcTestUtils.countRowsInTable(jdbcTemplate, "product");
        }

        @Test
        void shouldUpdateProductWhenIdDoesExist() {
            var peperoniPizza = ProductDomainFixtures.PEPERONI_PIZZA;
            jdbcProductRepository.save(peperoniPizza);

            peperoniPizza = peperoniPizza.toBuilder()
                    .name("modified name")
                    .build();

            jdbcProductRepository.save(peperoniPizza);

            JdbcTestUtils.countRowsInTable(jdbcTemplate, "product");
            assertModifiedFieldsAreFilled("product", peperoniPizza.getId().rawValue);
        }
    }

    @Nested
    class FindById {
        @Test
        void shouldThrowWhenIdIsNull() {
            assertThatThrownBy(() -> jdbcProductRepository.findById(null))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        void shouldReturnProductWhenIdExists() {
            jdbcProductRepository.save(ProductDomainFixtures.PEPERONI_PIZZA);

            var product = jdbcProductRepository.findById(ProductDomainFixtures.PEPERONI_PIZZA.getId());

            assertThat(product).isNotEmpty();
            assertThat(product.get()).isEqualTo(ProductDomainFixtures.PEPERONI_PIZZA);
        }

        @Test
        void shouldReturnEmptyWhenIdDoesNotExist() {
            var product = jdbcProductRepository.findById(ProductDomainFixtures.PEPERONI_PIZZA.getId());
            assertThat(product).isEmpty();
        }
    }

    @Nested
    class FindAllByState {
        @Test
        void shouldThrowWhenStateIsNull() {
            assertThatThrownBy(() -> jdbcProductRepository.findAllByState(null))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        void shouldReturnOnlyProductsShownWhenFindingByStateShown() {
            jdbcProductRepository.save(ProductDomainFixtures.PEPERONI_PIZZA);
            jdbcProductRepository.save(ProductDomainFixtures.DOUBLE_CHEESE_BURGUER);

            var products = jdbcProductRepository.findAllByState(ProductState.SHOWN);

            assertThat(products).containsOnly(ProductDomainFixtures.PEPERONI_PIZZA);
        }
    }

    @Nested
    class FindAll {
        @Test
        void shouldReturnAllProducts() {
            jdbcProductRepository.save(ProductDomainFixtures.PEPERONI_PIZZA);
            jdbcProductRepository.save(ProductDomainFixtures.DOUBLE_CHEESE_BURGUER);

            var products = jdbcProductRepository.findAll();

            assertThat(products).containsOnly(ProductDomainFixtures.PEPERONI_PIZZA, ProductDomainFixtures.DOUBLE_CHEESE_BURGUER);
        }
    }
}
