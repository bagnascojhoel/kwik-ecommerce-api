package br.com.kwikecommerce.api.restcontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

import static java.util.Objects.nonNull;


@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final HttpServletRequest mockRequest;

    @Override
    public String getKeycloakId() {
        return getPrincipal().getName();
    }

    @Override
    public boolean isAuthenticated() {
        return nonNull(getPrincipal());
    }

    private Principal getPrincipal() {
        return mockRequest.getUserPrincipal();
    }

}
