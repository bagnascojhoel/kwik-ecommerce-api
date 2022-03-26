package br.com.kwikecommerce.api.application.openapi;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;


@RequiredArgsConstructor
@Configuration
public class OpenApiConfiguration {

    private final ApiInfoProperties apiInfoProperties;

    @Bean
    public OpenAPI buildOpenApi() {
        var securitySchemeName = "bearerAuth";
        var securityScheme = new SecurityScheme()
            .scheme("bearer")
            .bearerFormat("JWT")
            .name(securitySchemeName)
            .type(SecurityScheme.Type.HTTP);

        var components = new Components();
        components.setSecuritySchemes(Map.of(securitySchemeName, securityScheme));

        return new OpenAPI()
            .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
            .components(components)
            .info(buildInfo());
    }

    private Info buildInfo() {
        return new Info()
            .title(apiInfoProperties.getTitle())
            .description(apiInfoProperties.getDescription())
            .version(apiInfoProperties.getVersion());
    }

}
