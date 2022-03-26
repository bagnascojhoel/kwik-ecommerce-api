package br.com.kwikecommerce.api.application.exceptionhandling;

import br.com.kwikecommerce.api.application.message.MessageProperty;


public class GatewayException extends BaseException {

    public GatewayException(MessageProperty messageProperty) {
        super(messageProperty);
    }

}
