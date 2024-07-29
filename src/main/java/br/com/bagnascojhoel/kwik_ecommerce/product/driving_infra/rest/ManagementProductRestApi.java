package br.com.bagnascojhoel.kwik_ecommerce.product.driving_infra.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

@Tags({
    @Tag(name = "/management"),
    @Tag(
        name = "/management/products",
        description = "Full management capabilities of products"
    )
})
public interface ManagementProductRestApi {

  @Operation(summary = "Create a product")
  ProductDto postProducts(
      final ProductDto productDto
  );

  @Operation(summary = "Gets all products")
  ProductCollectionDto getProducts();

  @Operation(summary = "Gets a single product by its ID")
  ProductDto getProduct(final String productId);

  @Operation(summary = "Updates a product with the filled properties")
  ProductDto patchProduct(
      @Schema(type = "string") final String productId,
      final ProductDto productDto
  );

}
