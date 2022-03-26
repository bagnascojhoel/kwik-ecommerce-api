package br.com.kwikecommerce.api.product;

import br.com.kwikecommerce.api.application.pagination.PageResponseDto;
import br.com.kwikecommerce.api.restcontroller.product.dto.ProductListingResponse;
import br.com.kwikecommerce.api.application.pagination.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface ProductService {

    Long createProduct(Product product);

    List<String> uploadImages(List<MultipartFile> files);

    Page<Product> findPage(PageRequest<ProductSortOption> pageRequest);

    PageResponseDto<ProductListingResponse> fetchPageByCategory(
        Long categoryId,
        PageRequest<ProductSortOption> pageRequest
    );

}
