package br.com.kwikecommerce.api.application.pagination;

import lombok.Value;

import java.util.List;


@Value
public class PageResponseDto<T> {
    List<T> content;
    long totalItems;
}
