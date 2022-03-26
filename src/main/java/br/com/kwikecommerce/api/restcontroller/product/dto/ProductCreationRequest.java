package br.com.kwikecommerce.api.restcontroller.product.dto;

import br.com.kwikecommerce.api.restcontroller.AllCategoriesExist;
import br.com.kwikecommerce.api.restcontroller.CompanyExists;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Set;


@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
@Schema(description = "Data necessary to add a product resource")
public class ProductCreationRequest {

    @Size(min = 3, max = 100)
    @Schema(
        description = "Max length is 100",
        example = "WebCam HD Lenovo"
    )
    private String title;

    @NotNull
    @Min(0)
    @Max(99999)
    @Schema(
        description = "Only two decimal places are used",
        maximum = "99999.99",
        example = "135.00"
    )
    private BigDecimal unitaryPrice;

    @NotNull
    @Min(0)
    @Max(65535)
    @Schema(
        description = "Quantity you currently have for this product",
        example = "37"
    )
    private Integer availableQty;

    @NotBlank
    @Schema(
        description = "Here you should write all necessary info of the product",
        example = """
            The webcam as a USB connector with a 2 meter cable. It also has a
            cover for the camera. It has a adjustable support which can be allows
            the camera to be used with any monitor or even without one, directly
            on your desk.
            """
    )
    private String details;

    @AllCategoriesExist
    @NotNull
    @Schema(
        description = "Define the category which this product belongs to",
        example = "[1]"
    )
    private Set<Long> categoriesIds;

    @CompanyExists
    @NotNull
    @Schema(
        description = "Id of the Company this product belongs to",
        example = "1"
    )
    private Long companyId;

}
