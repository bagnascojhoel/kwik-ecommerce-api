package br.com.bagnascojhoel.kwik_ecommerce.common.common_infra.logging;

import java.util.Set;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "kwik.logging")
public class KwikLoggingProperties {

  private final Set<String> whitelistedHeaders;
  private final Set<String> blacklistedPaths;

}
