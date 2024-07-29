package br.com.bagnascojhoel.kwik_ecommerce.common.common_infra.logging;


import jakarta.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

@Component
@AllArgsConstructor
public class RequestLoggingFilterResolver {

  private final KwikLoggingProperties kwikLoggingProperties;
  private final AntPathMatcher antPathMatcher = new AntPathMatcher();

  public boolean shouldSkipLogging(String requestURI) {
    return kwikLoggingProperties.getBlacklistedPaths().stream()
        .anyMatch(blacklistedPath -> antPathMatcher.match(blacklistedPath, requestURI));
  }

  public Map<String, String> getFilteredHeaders(HttpServletRequest request) {
    // Convert header names to a readable format
    return Collections.list(request.getHeaderNames()).stream()
        .filter(kwikLoggingProperties.getWhitelistedHeaders()::contains)
        .collect(Collectors.toMap(headerName -> headerName, request::getHeader));
  }

  public Map<String, String> getFilteredParameters(HttpServletRequest request) {
    // Convert parameter map to a readable format
    return Collections.list(request.getParameterNames())
        .stream()
        .collect(Collectors.toMap(
            paramName -> paramName,
            paramName -> String.join(",", request.getParameterValues(paramName))
        ));
  }
}
