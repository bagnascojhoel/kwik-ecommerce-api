package br.com.kwikecommerce.api.application.exceptionhandling;

import br.com.kwikecommerce.api.application.message.MessageProperty;
import lombok.Getter;


@Getter
public class BadRequestException extends BaseException {

    public BadRequestException(MessageProperty messageProperty) {
        super(messageProperty);
    }

}
