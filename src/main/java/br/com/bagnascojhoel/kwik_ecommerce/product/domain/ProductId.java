package br.com.bagnascojhoel.kwik_ecommerce.product.domain;

import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.UUID;

@EqualsAndHashCode
public class ProductId {

    @NonNull
    public final UUID rawValue;

    public ProductId(@NonNull String rawValue) {
        this.rawValue = UUID.fromString(rawValue);
    }

    public ProductId(@NonNull UUID rawValue) {
        this.rawValue = rawValue;
    }

    public static ProductId generate() {
        return new ProductId(UUID.randomUUID());
    }

    public String toString() {
        return rawValue.toString();
    }
}
