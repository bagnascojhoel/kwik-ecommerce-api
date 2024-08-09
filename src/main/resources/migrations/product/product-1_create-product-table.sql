--liquibase formatted sql

-- changeset bagnascojhoel:product-1_create-product-table
CREATE TABLE product.product (
    id UUID PRIMARY KEY NOT NULL,
    state VARCHAR(30) NOT NULL,
    name VARCHAR(300) NOT NULL,
    description VARCHAR(70),
    price_in_brl NUMERIC NOT NULL,
    image_url VARCHAR(2048),
    created_by VARCHAR(50) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    modified_by VARCHAR(50),
    modified_at TIMESTAMP WITH TIME ZONE
);