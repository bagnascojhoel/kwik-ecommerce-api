package br.com.bagnascojhoel.kwik_ecommerce.product.driven_infra.database;

import br.com.bagnascojhoel.kwik_ecommerce.common.infrastructure.CommonTestConstants;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.TemporalUnitWithinOffset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@JdbcTest(properties = {
    "spring.datasource.url=" + CommonTestConstants.TEST_CONTAINER_POSTGRES_JDBC_URL,
    "spring.datasource.driver-class-name=org.testcontainers.jdbc.ContainerDatabaseDriver"
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(JdbcConfiguration.class)
public abstract class AbstractJdbcRepositoryTest {

  @Autowired
  protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Autowired
  protected JdbcTemplate jdbcTemplate;

  protected void assertModifiedFieldsAreFilled(
      final String table,
      final UUID id
  ) {
    namedParameterJdbcTemplate.queryForStream(
        "SELECT modified_at, modified_by FROM " + table + " WHERE id = :id",
        Map.of("id", id),
        (rs, rowNum) -> new AuditModified(rs.getString("modified_by"),
            rs.getTimestamp("modified_at").toLocalDateTime())
    ).forEach(result -> {
      Assertions.assertThat(result.getModifiedBy()).isNotBlank();
      Assertions.assertThat(result.getModifiedAt())
          .isCloseTo(LocalDateTime.now(), new TemporalUnitWithinOffset(1, ChronoUnit.MINUTES));
    });
  }

  @AllArgsConstructor
  @Getter
  private final class AuditModified {

    private final String modifiedBy;
    private final LocalDateTime modifiedAt;
  }
}
