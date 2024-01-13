package br.com.bagnascojhoel.kwik_ecommerce.product.domain;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
@Getter
public enum ProductState {
    SHOWN,
    HIDDEN,
    ARCHIVED;
}
