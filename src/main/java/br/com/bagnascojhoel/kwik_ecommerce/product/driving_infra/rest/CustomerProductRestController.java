package br.com.bagnascojhoel.kwik_ecommerce.product.driving_infra.rest;

import br.com.bagnascojhoel.kwik_ecommerce.product.application.ProductApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer/products")
@AllArgsConstructor
public class CustomerProductRestController {

    private final ProductApplicationService productApplicationService;

    private final ProductDtoFactory productDtoFactory;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ProductCollectionDto getProducts() {
        return productDtoFactory.createCollection(
                productApplicationService.findAllProductsToShowCustomers()
        );
    }

}
