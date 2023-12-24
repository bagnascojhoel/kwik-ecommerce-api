package br.com.bagnascojhoel.kwik_ecommerce.common.domain;

import lombok.NonNull;

public abstract class AbstractResourceNotFoundException extends KwikEcommerceException {
    public AbstractResourceNotFoundException(@NonNull String message, String errorCode) {
        super(message, errorCode);
    }
}
