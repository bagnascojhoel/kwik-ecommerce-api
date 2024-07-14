package br.com.bagnascojhoel.kwik_ecommerce.product.driving_infra.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

@Tags({
    @Tag(
        name = "/customer/products",
        description = "Operations around products for customers"
    ),
    @Tag(
        name = "/customer",
        description = "Operations used by customers"
    )
})
public interface CustomerProductRestApi {

  @Operation(summary = "Gets all products visible for the customer")
  ProductCollectionDto getProducts();
  
}
