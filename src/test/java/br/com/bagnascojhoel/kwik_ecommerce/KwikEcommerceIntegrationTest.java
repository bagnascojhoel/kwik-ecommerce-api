package br.com.bagnascojhoel.kwik_ecommerce;

import static br.com.bagnascojhoel.kwik_ecommerce.common.infrastructure.CommonTestConstants.TEST_CONTAINER_POSTGRES_JDBC_URL;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import org.assertj.core.data.TemporalUnitWithinOffset;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
    classes = KwikEcommerceApplication.class,
    properties = {
        "spring.datasource.url=" + TEST_CONTAINER_POSTGRES_JDBC_URL,
        "spring.datasource.driver-class-name=org.testcontainers.jdbc.ContainerDatabaseDriver"
    }
)
public class KwikEcommerceIntegrationTest {

  @Test
  void applicationShouldUseBrazilianTimeZone() {
    ZoneId brazilTimeZone = ZoneId.of("America/Sao_Paulo");
    LocalDateTime localDateTime = LocalDateTime.now(brazilTimeZone);

    assertThat(ZoneId.systemDefault()).isEqualTo(brazilTimeZone);
    assertThat(LocalDate.now()).isEqualTo(localDateTime.toLocalDate());
    assertThat(LocalDateTime.now()).isCloseTo(localDateTime,
        new TemporalUnitWithinOffset(1, ChronoUnit.MINUTES));
    assertThat(ZonedDateTime.now().getZone()).isEqualTo(brazilTimeZone);
    assertThat(Clock.systemDefaultZone()).isEqualTo(Clock.system(brazilTimeZone));
  }
}
