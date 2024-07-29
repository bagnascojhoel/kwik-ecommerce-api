package br.com.bagnascojhoel.kwik_ecommerce.product.driving_infra.rest;

import br.com.bagnascojhoel.kwik_ecommerce.product.application.ProductApplicationService;
import br.com.bagnascojhoel.kwik_ecommerce.product.domain.ProductId;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/management/products")
@AllArgsConstructor
public class ManagementProductRestController implements ManagementProductRestApi {

  private final ProductApplicationService productApplicationService;

  private final ProductDtoFactory productDtoFactory;

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public ProductDto postProducts(@RequestBody final ProductDto productDto) {
    var product = productDtoFactory.createSaveCommand(productDto);
    var productId = productApplicationService.saveProduct(null, product);
    return productDtoFactory.create(productId);
  }

  @PatchMapping("/{productId}")
  @ResponseStatus(HttpStatus.OK)
  public ProductDto patchProduct(
      @PathVariable final String productId,
      @RequestBody final ProductDto dto
  ) {
    var saveProduct = productDtoFactory.createSaveCommand(dto);
    var product = productApplicationService.saveProduct(new ProductId(productId), saveProduct);
    return productDtoFactory.create(product);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public ProductCollectionDto getProducts() {
    return productDtoFactory.createCollection(productApplicationService.findAllProducts());
  }

  @GetMapping("/{productId}")
  @ResponseStatus(HttpStatus.OK)
  public ProductDto getProduct(@PathVariable final String productId) {
    return productDtoFactory.create(
        productApplicationService.getProductById(new ProductId(productId))
    );
  }

}
