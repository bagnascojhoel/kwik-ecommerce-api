package br.com.bagnascojhoel.kwik_ecommerce.product.infrastructure.driving_ports.rest;

import br.com.bagnascojhoel.kwik_ecommerce.product.application.ProductApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RestBasePaths.CUSTOMER_PRODUCTS)
@AllArgsConstructor
public class CustomerProductRestController {

    private final ProductApplicationService productApplicationService;

    private final ProductResourceFactory productResourceFactory;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ProductCollectionRepresentation getProducts() {
        return productResourceFactory.createCollection(
                productApplicationService.findAllProductsToShowCustomers()
        );
    }

}
