package br.com.kwikecommerce.api.category;

import br.com.kwikecommerce.api.restcontroller.category.dto.CategoryCreationRequestDto;
import br.com.kwikecommerce.api.restcontroller.product.dto.ProductCreationRequest;
import org.mapstruct.Mapper;


@Mapper
public interface CategoryProductMapper {

    CategoryCreationRequestDto map(ProductCreationRequest productCreationRequest);

}
