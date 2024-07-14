package br.com.bagnascojhoel.kwik_ecommerce.product.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ProductState {
  SHOWN,
  HIDDEN,
  ARCHIVED
}
