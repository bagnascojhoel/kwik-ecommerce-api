package br.com.kwikecommerce.api.application.openapi;


import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@Getter
@Data
@ConfigurationProperties("kwik-ecommerce.api-info")
public class ApiInfoProperties {

    private String title;
    private String description;
    private String version;

}
