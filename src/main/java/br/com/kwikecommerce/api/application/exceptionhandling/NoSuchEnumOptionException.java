package br.com.kwikecommerce.api.application.exceptionhandling;

public class NoSuchEnumOptionException extends Exception {

    public NoSuchEnumOptionException(String invalidEnumOption) {
        super(invalidEnumOption);
    }

}
