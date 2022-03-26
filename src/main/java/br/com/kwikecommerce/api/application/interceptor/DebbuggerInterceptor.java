package br.com.kwikecommerce.api.application.interceptor;

import br.com.kwikecommerce.api.application.logging.LogService;
import br.com.kwikecommerce.api.restcontroller.AuthenticationService;
import br.com.kwikecommerce.api.application.message.MessageProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
@RequiredArgsConstructor
public class DebbuggerInterceptor implements HandlerInterceptor {

    private static final String NOT_AUTHENTICATED = "not authenticated";
    private static final String USER_AGENT_HEADER = "user-agent";

    private final LogService logService;
    private final AuthenticationService authenticationService;

    @Override
    public boolean preHandle(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler
    ) throws Exception {
        var authentication = authenticationService.isAuthenticated()
            ? authenticationService.getKeycloakId()
            : NOT_AUTHENTICATED;

        var userAgent = request.getHeader(USER_AGENT_HEADER);

        logService.logInfo(
            MessageProperty.use(
                "log.debbugger-interceptor.before-request",
                request.getMethod(),
                request.getServletPath(),
                userAgent,
                authentication
            )
        );

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler,
        Exception ex
    ) throws Exception {
        var authentication = authenticationService.isAuthenticated()
            ? authenticationService.getKeycloakId()
            : NOT_AUTHENTICATED;
        var userAgent = request.getHeader(USER_AGENT_HEADER);

        logService.logInfo(
            MessageProperty.use(
                "log.debbugger-interceptor.after-request",
                request.getMethod(),
                request.getServletPath(),
                userAgent,
                authentication
            )
        );

        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

}
