package br.com.bagnascojhoel.kwik_ecommerce.product.infrastructure.driving_ports.rest;

import br.com.bagnascojhoel.kwik_ecommerce.product.application.ProductApplicationService;
import br.com.bagnascojhoel.kwik_ecommerce.product.domain.ProductId;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(RestBasePaths.MANAGER_PRODUCTS)
@AllArgsConstructor
public class ProductRestController {

    private final ProductApplicationService productApplicationService;

    private final ProductResourceFactory productResourceFactory;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postProducts(@RequestBody final ProductRepresentation productRepresentation) {
        var product = productResourceFactory.createSaveCommand(productRepresentation);
        productApplicationService.saveProduct(product);
        // TODO Change this to return the productId
        //  - Consider that the front-end would like to redirect the user to the details page after creating it
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ProductCollectionRepresentation getProducts() {
        return productResourceFactory.createCollection(productApplicationService.findAllProducts());
    }

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ProductRepresentation getProduct(@PathVariable final ProductId productId) {
        return productResourceFactory.create(
                productApplicationService.getProductById(productId)
        );
    }

    @PostMapping("/{productId}/archive")
    @ResponseStatus(HttpStatus.OK)
    public void postArchiveProduct(@PathVariable final ProductId productId) {
        productApplicationService.archiveProduct(productId);
    }

    @PostMapping("/{productId}/hide")
    @ResponseStatus(HttpStatus.OK)
    public void postHideProduct(@PathVariable final ProductId productId) {
        productApplicationService.hideProduct(productId);
    }

    @PostMapping("/{productId}/show")
    @ResponseStatus(HttpStatus.OK)
    public void postShowProduct(@PathVariable final ProductId productId) {
        productApplicationService.showProduct(productId);
    }

}
