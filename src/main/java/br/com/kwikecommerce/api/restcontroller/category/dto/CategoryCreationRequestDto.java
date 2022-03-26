package br.com.kwikecommerce.api.restcontroller.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCreationRequestDto {

    @NotBlank
    @Size(min = 3, max = 30)
    @Schema(example = "Mugs")
    private String title;

    @NotNull
    @Schema(example = "1")
    private Long companyId;

}
