package br.com.kwikecommerce.api.restcontroller.product;

import br.com.kwikecommerce.api.application.pagination.PageRequest;
import br.com.kwikecommerce.api.application.pagination.PageResponseDto;
import br.com.kwikecommerce.api.application.pagination.PaginationMapper;
import br.com.kwikecommerce.api.restcontroller.product.dto.ProductCreationRequest;
import br.com.kwikecommerce.api.restcontroller.product.dto.ProductListingResponse;
import br.com.kwikecommerce.api.product.ProductMapper;
import br.com.kwikecommerce.api.product.ProductService;
import br.com.kwikecommerce.api.product.ProductSortOption;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;


@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController implements ProductApi {

    private final ProductService productService;
    private final ProductMapper productMapper;
    private final PaginationMapper paginationMapper;

    @Override
    @PostMapping
    public Long create(@RequestPart ProductCreationRequest request, @RequestPart List<MultipartFile> images) {
        var imagesUrls = productService.uploadImages(images);
        var product = productMapper.map(request, imagesUrls);
        return productService.createProduct(product);
    }

    @Override
    @GetMapping
    public PageResponseDto<ProductListingResponse> findPage(@Valid PageRequest<ProductSortOption> pageRequest) {
        var page = productService.findPage(pageRequest)
            .map(productMapper::map);

        return paginationMapper.map(page);
    }

    // TODO mover esse método para /categories/{categoryId}/products
    @Override
    @GetMapping("/{categoryId}")
    public PageResponseDto<ProductListingResponse> fetchPageByCategory(
        Long categoryId,
        PageRequest<ProductSortOption> pageRequest
    ) {
        return productService.fetchPageByCategory(categoryId, pageRequest);
    }
}
