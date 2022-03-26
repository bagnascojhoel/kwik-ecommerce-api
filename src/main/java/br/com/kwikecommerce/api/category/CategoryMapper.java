package br.com.kwikecommerce.api.category;

import br.com.kwikecommerce.api.restcontroller.category.dto.CategoryCreationRequestDto;
import br.com.kwikecommerce.api.restcontroller.category.dto.CategoryListingResponse;
import br.com.kwikecommerce.api.company.CompanyMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(uses = CompanyMapper.class)
public interface CategoryMapper {

    @Mapping(target = "company", source = "companyId")
    Category map(CategoryCreationRequestDto request);

    CategoryListingResponse map(Category category);

    default Category mapCategoryIdToCategory(Long categoryId) {
        return Category.builder()
            .id(categoryId)
            .build();
    }

    default Long mapCategoryToCategoryId(Category category) {
        return category.getId();
    }

}
