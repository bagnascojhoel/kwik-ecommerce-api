package br.com.bagnascojhoel.kwik_ecommerce.product.driven_infra.database;

import br.com.bagnascojhoel.kwik_ecommerce.product.domain.Product;
import br.com.bagnascojhoel.kwik_ecommerce.product.domain.ProductId;
import br.com.bagnascojhoel.kwik_ecommerce.product.domain.ProductRepository;
import br.com.bagnascojhoel.kwik_ecommerce.product.domain.ProductState;
import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
class JdbcProductRepository implements ProductRepository {

  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
  private final ProductParameterSource parameterSource;
  private final ProductRowMapper productRowMapper;

  public JdbcProductRepository(
      @Qualifier("productJdbcTemplate") final NamedParameterJdbcTemplate namedParameterJdbcTemplate,
      final ProductParameterSource parameterSource,
      final ProductRowMapper productRowMapper
  ) {
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    this.parameterSource = parameterSource;
    this.productRowMapper = productRowMapper;
  }

  @Override
  public void save(@NonNull final Product product) {
    namedParameterJdbcTemplate.update(ProductSql.UPSERT, parameterSource.forUpsert(product));
  }

  @Override
  public Optional<Product> findById(@NonNull final ProductId productId) {
    try {
      return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(
          ProductSql.FIND_BY_ID,
          parameterSource.onlyId(productId),
          productRowMapper
      ));
    } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
      return Optional.empty();
    }
  }

  @Override
  public List<Product> findAllByState(@NonNull final ProductState productState) {
    return namedParameterJdbcTemplate.query(
        ProductSql.FIND_BY_STATE,
        parameterSource.onlyState(productState),
        productRowMapper
    );
  }

  @Override
  public List<Product> findAll() {
    return namedParameterJdbcTemplate.query(
        ProductSql.FIND_ALL,
        productRowMapper
    );
  }
}
