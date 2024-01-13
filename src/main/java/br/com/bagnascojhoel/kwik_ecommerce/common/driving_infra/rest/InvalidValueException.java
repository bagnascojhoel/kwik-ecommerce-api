package br.com.bagnascojhoel.kwik_ecommerce.common.driving_infra.rest;

public class InvalidValueException extends RuntimeException {
    public InvalidValueException(String message) {
        super(message);
    }
}
