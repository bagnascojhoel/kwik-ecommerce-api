package br.com.bagnascojhoel.kwik_ecommerce.common.common_infra;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Data
@ConfigurationProperties(prefix = "kwik.logging")
public class KwikLoggingProperties {

    private final Set<String> blacklistedHeaders;
}
