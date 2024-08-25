package br.com.bagnascojhoel.kwik_ecommerce.product.driven_infra.database;

import javax.sql.DataSource;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
@AllArgsConstructor
public class JdbcConfiguration {

  @Bean("productDataSource")
  public DataSource dataSource(DataSourceProperties dataSourceProperties) {
    final String connectionUrl = dataSourceProperties.getUrl().concat("&currentSchema=product");
    return dataSourceProperties.initializeDataSourceBuilder().url(connectionUrl).build();
  }

  @Bean("productJdbcTemplate")
  public NamedParameterJdbcTemplate namedParameterJdbcTemplate(
      @Qualifier("productDataSource") final DataSource dataSource
  ) {
    return new NamedParameterJdbcTemplate(dataSource);
  }
}
