package br.com.kwikecommerce.api.product;

import br.com.kwikecommerce.api.category.CategoryMapper;
import br.com.kwikecommerce.api.company.CompanyMapper;
import br.com.kwikecommerce.api.restcontroller.product.dto.ProductCreationRequest;
import br.com.kwikecommerce.api.restcontroller.product.dto.ProductListingResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(uses = {CategoryMapper.class, CompanyMapper.class})
public interface ProductMapper {

    @Mapping(target = "imagesUrls", source = "imagesUrls")
    @Mapping(target = "categories", source = "request.categoriesIds")
    @Mapping(target = "company", source = "request.companyId")
    Product map(ProductCreationRequest request, List<String> imagesUrls);

    @Mapping(target = "categoriesIds", source = "categories")
    ProductListingResponse map(Product product);

}
