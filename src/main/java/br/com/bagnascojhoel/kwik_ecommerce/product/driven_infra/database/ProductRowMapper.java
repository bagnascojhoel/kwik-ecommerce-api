package br.com.bagnascojhoel.kwik_ecommerce.product.driven_infra.database;

import br.com.bagnascojhoel.kwik_ecommerce.product.domain.Product;
import br.com.bagnascojhoel.kwik_ecommerce.product.domain.ProductId;
import br.com.bagnascojhoel.kwik_ecommerce.product.domain.ProductState;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
class ProductRowMapper implements RowMapper<Product> {

    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        if (rs.getString("id") == null) {
            return null;
        }

        return Product.builder()
                .id(new ProductId(rs.getString("id")))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .priceInBrl(rs.getBigDecimal("price_in_brl"))
                .state(ProductState.valueOf(rs.getString("state")))
                .imageUrl(rs.getString("image_url"))
                .build();
    }
}
