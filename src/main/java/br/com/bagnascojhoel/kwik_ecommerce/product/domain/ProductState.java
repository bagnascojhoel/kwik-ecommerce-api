package br.com.bagnascojhoel.kwik_ecommerce.product.domain;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
@Getter
public enum ProductState {
    SHOWN("product-state-shown"),
    HIDDEN("product-state-hidden"),
    ARCHIVED("product-state-archived");

    private final String code;
    
    @Nullable
    public static ProductState getFromCode(@NonNull final String code) {
        for (ProductState state : values()) {
            if (state.getCode().equals(code)) {
                return state;
            }
        }

        return null;
    }
}
