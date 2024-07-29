package br.com.bagnascojhoel.kwik_ecommerce.common.common_infra;

import br.com.bagnascojhoel.kwik_ecommerce.common.common_infra.logging.RequestLoggingFilterResolver;
import br.com.bagnascojhoel.kwik_ecommerce.common.common_infra.logging.RequestLoggingInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  private final RequestLoggingFilterResolver requestLoggingFilterResolver;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(
        new RequestLoggingInterceptor(requestLoggingFilterResolver));
  }

  @Override
  public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
    configurer.defaultContentType(MediaType.APPLICATION_JSON);
  }

}
