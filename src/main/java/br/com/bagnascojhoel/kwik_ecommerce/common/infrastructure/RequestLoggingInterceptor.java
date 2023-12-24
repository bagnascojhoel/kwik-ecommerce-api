package br.com.bagnascojhoel.kwik_ecommerce.common.infrastructure;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class RequestLoggingInterceptor implements HandlerInterceptor {

    private static final String START_TIME_ATTRIBUTE = "requestStartTime";

    private final Set<String> blacklistedHeaders;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        request.setAttribute(START_TIME_ATTRIBUTE, System.currentTimeMillis());

        // TODO: Add a uniqueIdentifier to the request and propagate it
        log.info(
                "request started, method={}, uri={}, params={}, headers={}",
                request.getMethod(),
                request.getRequestURI(),
                getRequestParameters(request),
                getRequestHeaders(request)
        );
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        Long startTime = (Long) request.getAttribute(START_TIME_ATTRIBUTE);

        long elapsedTime = System.currentTimeMillis() - startTime;

        log.info(
                "request finished, method={}, uri={}, params={}, headers={}, duration={}, responseCode={}",
                request.getMethod(),
                request.getRequestURI(),
                getRequestParameters(request),
                getRequestHeaders(request),
                elapsedTime,
                response.getStatus()
        );
    }


    private Map<String, String> getRequestParameters(HttpServletRequest request) {
        // Convert parameter map to a readable format
        return Collections.list(request.getParameterNames())
                .stream()
                .collect(Collectors.toMap(
                        paramName -> paramName,
                        paramName -> String.join(",", request.getParameterValues(paramName))
                ));
    }

    private Map<String, String> getRequestHeaders(HttpServletRequest request) {
        // Convert header names to a readable format
        return Collections.list(request.getHeaderNames())
                .stream()
                .collect(Collectors.toMap(
                        headerName -> headerName,
                        header -> blacklistedHeaders.contains(header) ? "<blacklisted>" : request.getHeader(header)
                ));
    }
}
