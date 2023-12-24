package br.com.bagnascojhoel.kwik_ecommerce.product.infrastructure.driven_ports.product;

class ProductSql {
    protected static final String UPSERT = """
            INSERT INTO product (id, name, description, price_in_brl, state, image_url, created_by, created_at)
            VALUES (
                :id,
                :name,
                :description,
                :price_in_brl,
                :state,
                :image_url,
                :created_by,
                :created_at
            )
            ON CONFLICT (id) DO UPDATE SET
                name = :name,
                description = :description,
                price_in_brl = :price_in_brl,
                state = :state,
                image_url = :image_url,
                modified_by = :modified_by,
                modified_at = :modified_at;
            """;

    protected static final String FIND_BY_ID = """
            SELECT
                id,
                name,
                description,
                price_in_brl,
                state,
                image_url,
                created_by,
                created_at,
                modified_by,
                modified_at
            FROM product
            WHERE id = :id
            """;

    protected static final String FIND_BY_STATE = """
            SELECT
                id,
                name,
                description,
                price_in_brl,
                state,
                image_url,
                created_by,
                created_at,
                modified_by,
                modified_at
            FROM product
            WHERE state = :state
            """;

    protected static final String FIND_ALL = """
            SELECT
                id,
                name,
                description,
                price_in_brl,
                state,
                image_url,
                created_by,
                created_at,
                modified_by,
                modified_at
            FROM product
            """;
}
