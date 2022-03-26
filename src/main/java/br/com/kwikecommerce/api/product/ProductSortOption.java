package br.com.kwikecommerce.api.product;

import br.com.kwikecommerce.api.application.pagination.sort.SortOption;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Schema(name = "Product Sorting Option")
@RequiredArgsConstructor
public enum ProductSortOption implements SortOption {
    TITLE(Product.Fields.title),
    PRICE(Product.Fields.unitaryPrice);

    @Getter
    private final String propertyName;

}