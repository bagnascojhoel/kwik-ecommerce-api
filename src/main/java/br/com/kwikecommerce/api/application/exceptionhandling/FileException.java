package br.com.kwikecommerce.api.application.exceptionhandling;

import br.com.kwikecommerce.api.application.message.MessageProperty;


public class FileException extends BaseException {

    public FileException(MessageProperty messageProperty) {
        super(messageProperty);
    }

}
