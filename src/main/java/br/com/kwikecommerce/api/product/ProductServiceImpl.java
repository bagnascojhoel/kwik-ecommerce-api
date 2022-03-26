package br.com.kwikecommerce.api.product;

import br.com.kwikecommerce.api.application.pagination.PageRequest;
import br.com.kwikecommerce.api.application.pagination.PageResponseDto;
import br.com.kwikecommerce.api.application.pagination.PaginationMapper;
import br.com.kwikecommerce.api.application.storage.StorageService;
import br.com.kwikecommerce.api.restcontroller.product.dto.ProductListingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

import static br.com.kwikecommerce.api.application.storage.Storage.PRODUCT_IMAGES;


@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final StorageService storageService;
    private final PaginationMapper paginationMapper;

    @Override
    public Long createProduct(Product product) {
        return productRepository.save(product).getId();
    }

    @Override
    public List<String> uploadImages(List<MultipartFile> files) {
        return files.isEmpty()
            ? storageService.upload(PRODUCT_IMAGES, files)
            : Collections.emptyList();
    }

    @Override
    public Page<Product> findPage(PageRequest<ProductSortOption> pageRequest) {
        return productRepository.findAll(pageRequest);
    }

    @Override
    public PageResponseDto<ProductListingResponse> fetchPageByCategory(
        Long categoryId,
        PageRequest<ProductSortOption> pageRequest
    ) {
        var page = productRepository.findByCategories_id(categoryId, pageRequest)
            .map(productMapper::map);

        return paginationMapper.map(page);
    }

}
