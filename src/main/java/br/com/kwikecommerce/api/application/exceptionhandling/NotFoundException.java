package br.com.kwikecommerce.api.application.exceptionhandling;


import br.com.kwikecommerce.api.application.message.MessageProperty;


public class NotFoundException extends BaseException {

    public NotFoundException(MessageProperty messageProperty) {
        super(messageProperty);
    }

}
