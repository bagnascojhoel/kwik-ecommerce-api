package br.com.kwikecommerce.api.restcontroller.category;

import br.com.kwikecommerce.api.restcontroller.category.dto.CategoryCreationRequestDto;
import br.com.kwikecommerce.api.restcontroller.category.dto.CategoryListingResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;


@Tag(
    name = "Categories",
    description = "Operations over category resources"
)
public interface CategoryApi {

    @Operation(summary = "Create a new category")
    Long create(@RequestBody @Valid CategoryCreationRequestDto categoryCreationRequestDto);

    @Operation(summary = "List all categories")
    List<CategoryListingResponse> listAll();

}
