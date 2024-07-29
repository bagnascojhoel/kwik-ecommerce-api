package br.com.bagnascojhoel.kwik_ecommerce.common.common_infra.logging;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@RequiredArgsConstructor
public class RequestLoggingInterceptor implements HandlerInterceptor {

  private static final String START_TIME_ATTRIBUTE = "requestStartTime";

  private final RequestLoggingFilterResolver requestLoggingFilterResolver;

  @Override
  public boolean preHandle(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler
  ) {
    if (requestLoggingFilterResolver.shouldSkipLogging(request.getRequestURI())) {
      return true;
    }

    request.setAttribute(START_TIME_ATTRIBUTE, System.currentTimeMillis());

    // TODO: Add a uniqueIdentifier to the request and propagate it
    log.info(
        "request started, method={}, uri={}, params={}, headers={}",
        request.getMethod(),
        request.getRequestURI(),
        requestLoggingFilterResolver.getFilteredParameters(request),
        requestLoggingFilterResolver.getFilteredHeaders(request)
    );
    return true;
  }

  @Override
  public void afterCompletion(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler,
      Exception ex
  ) throws Exception {
    if (requestLoggingFilterResolver.shouldSkipLogging(request.getRequestURI())) {
      return;
    }

    final long startTime = (long) request.getAttribute(START_TIME_ATTRIBUTE);
    final long elapsedTime = System.currentTimeMillis() - startTime;

    log.info(
        "request finished, method={}, uri={}, params={}, headers={}, duration={}, response-code={}",
        request.getMethod(),
        request.getRequestURI(),
        requestLoggingFilterResolver.getFilteredParameters(request),
        requestLoggingFilterResolver.getFilteredHeaders(request),
        elapsedTime,
        response.getStatus()
    );
  }
}
