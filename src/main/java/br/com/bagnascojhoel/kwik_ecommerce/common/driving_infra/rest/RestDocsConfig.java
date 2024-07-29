package br.com.bagnascojhoel.kwik_ecommerce.common.driving_infra.rest;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Kwik-Ecommerce API",
        description = "Documentation of all endpoints available for kwik.",
        version = "beta"
    ),
    tags = {
        @Tag(name = "/management", description = "Management operations"),
        @Tag(name = "/customer", description = "Customer operations")
    }
)
public class RestDocsConfig {

}
