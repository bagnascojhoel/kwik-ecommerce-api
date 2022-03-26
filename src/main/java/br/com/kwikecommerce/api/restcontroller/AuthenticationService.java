package br.com.kwikecommerce.api.restcontroller;

public interface AuthenticationService {

    String getKeycloakId();

    boolean isAuthenticated();

}
