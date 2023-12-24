package br.com.bagnascojhoel.kwik_ecommerce.common.domain;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.NonNull;

public class KwikEcommerceException extends RuntimeException {
    @Getter
    @Nullable
    private final String errorCode;

    public KwikEcommerceException(@NonNull String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public KwikEcommerceException(@NonNull String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}
