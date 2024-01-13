package br.com.bagnascojhoel.kwik_ecommerce.product.driving_infra.rest;

import br.com.bagnascojhoel.kwik_ecommerce.common.driving_infra.rest.InvalidValueException;
import br.com.bagnascojhoel.kwik_ecommerce.product.application.ProductApplicationService;
import br.com.bagnascojhoel.kwik_ecommerce.product.domain.ProductId;
import br.com.bagnascojhoel.kwik_ecommerce.product.domain.ProductState;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductRestController {

    private final ProductApplicationService productApplicationService;

    private final ProductDtoFactory productDtoFactory;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto postProducts(@RequestBody final ProductDto productDto) {
        var product = productDtoFactory.createSaveCommand(productDto);
        var productId = productApplicationService.saveProduct(product);
        return productDtoFactory.create(productId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ProductCollectionDto getProducts() {
        return productDtoFactory.createCollection(productApplicationService.findAllProducts());
    }

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto getProduct(@PathVariable final ProductId productId) {
        return productDtoFactory.create(
                productApplicationService.getProductById(productId)
        );
    }

    @PutMapping("/{productId}/state")
    @ResponseStatus(HttpStatus.OK)
    public void putState(
            @PathVariable final ProductId productId,
            @RequestBody final ProductDto dto
    ) {
        if(dto.getProductState().getCode().equals(ProductState.ARCHIVED.name())){
            productApplicationService.archiveProduct(productId);
        } else if(dto.getProductState().getCode().equals(ProductState.SHOWN.name())){
            productApplicationService.showProduct(productId);
        } else if(dto.getProductState().getCode().equals(ProductState.HIDDEN.name())){
            productApplicationService.hideProduct(productId);
        } else {
            throw new InvalidValueException("/errors/client-errors/product/invalid-state");
        }
    }

}
