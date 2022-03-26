package br.com.kwikecommerce.api.restcontroller.product;

import br.com.kwikecommerce.api.application.pagination.PageRequest;
import br.com.kwikecommerce.api.application.pagination.PageResponseDto;
import br.com.kwikecommerce.api.restcontroller.product.dto.ProductCreationRequest;
import br.com.kwikecommerce.api.restcontroller.product.dto.ProductListingResponse;
import br.com.kwikecommerce.api.product.ProductSortOption;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;


@Tag(
    name = "Products",
    description = "Operations over product resources"
)
public interface ProductApi {

    @Operation(
        summary = "Create",
        description = """
            On Swagger this endpoint doesn't work correctly. Please, use an API Client which
            supports file upload via Multipart Form Data (e.g. Postman).
            """
    )
    Long create(@Valid ProductCreationRequest request, List<MultipartFile> images);

    @Operation(summary = "Find page")
    PageResponseDto<ProductListingResponse> findPage(
        @Valid PageRequest<ProductSortOption> pageRequest
    );

    @Operation(summary = "Fetch a page of products from a given category")
    @Parameter(name = "categoryId", in = ParameterIn.PATH, example = "1")
    @Parameter(name = "sortingOption")
    @Parameter(name = "pageNumber", example = "0")
    PageResponseDto<ProductListingResponse> fetchPageByCategory(
        @PathVariable Long categoryId,
        PageRequest<ProductSortOption> pageRequest
    );

}
