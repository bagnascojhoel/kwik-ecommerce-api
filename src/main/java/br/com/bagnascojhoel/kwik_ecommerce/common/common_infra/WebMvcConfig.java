package br.com.bagnascojhoel.kwik_ecommerce.common.common_infra;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  private final KwikLoggingProperties kwikLoggingProperties;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(
        new RequestLoggingInterceptor(kwikLoggingProperties.getBlacklistedHeaders()));
  }

  @Override
  public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
    configurer.defaultContentType(MediaType.APPLICATION_JSON);
  }
  
}
